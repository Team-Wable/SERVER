package com.wable.www.WableServer.api.auth.service.Impl;

import com.wable.www.WableServer.api.auth.SocialPlatform;
import com.wable.www.WableServer.api.auth.dto.SocialInfoDto;
import com.wable.www.WableServer.api.auth.dto.response.AuthResponseDto;
import com.wable.www.WableServer.api.auth.dto.response.AuthTokenResponseDto;
import com.wable.www.WableServer.api.auth.dto.request.AuthRequestDto;
import com.wable.www.WableServer.api.auth.service.AppleAuthService;
import com.wable.www.WableServer.api.auth.service.AuthService;
import com.wable.www.WableServer.api.auth.service.KakaoAuthService;
import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.common.exception.BadRequestException;
import com.wable.www.WableServer.common.response.ErrorStatus;
import com.wable.www.WableServer.common.config.jwt.JwtTokenProvider;
import com.wable.www.WableServer.common.config.jwt.UserAuthentication;
import com.wable.www.WableServer.common.util.MemberUtil;
import com.wable.www.WableServer.external.slack.service.SlackService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${aws-property.s3-default-image-url}")
    private String GHOST_IMAGE_S3;
    private final static String DEFAULT_NICKNAME="";

    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoAuthService kakaoAuthService;
    private final AppleAuthService appleAuthService;
    private final MemberRepository memberRepository;
    private final SlackService slackService;

    @Override
    @Transactional
    public AuthResponseDto socialLogin(String socialAccessToken, AuthRequestDto authRequestDto) throws NoSuchAlgorithmException, InvalidKeySpecException {

        val socialPlatform = SocialPlatform.valueOf(authRequestDto.getSocialPlatform());
        SocialInfoDto socialData = getSocialData(socialPlatform, socialAccessToken, authRequestDto.getUserName());
        String refreshToken = jwtTokenProvider.generateRefreshToken();
        Boolean isExistUser = isMemberBySocialId(socialData.getId());


        try {
            // 신규 유저 저장
            if (!isExistUser.booleanValue()) {
                Member member = Member.builder()
                        .nickname(DEFAULT_NICKNAME)//.nickname(socialData.getNickname())
                        .socialPlatform(socialPlatform)
                        .socialId(socialData.getId())
                        .profileUrl(GHOST_IMAGE_S3)
                        .memberEmail(socialData.getEmail())
                        .socialNickname(socialData.getNickname())
                        .build();

                memberRepository.save(member);

                slackService.sendSlackMessage(memberRepository.count(), "#wable-signup");

                Authentication authentication = new UserAuthentication(member.getId(), null, null);

                String accessToken = jwtTokenProvider.generateAccessToken(authentication);
                member.updateRefreshToken(refreshToken);

                int memberLevel = MemberUtil.refineMemberExpToLevel(member.getMemberExp());

                //todo : 추후 팀 로고를 통해서 저장된 s3값 가져오는 함수 적용
                String memberFanTeamLogo = "";

                return AuthResponseDto.of(member.getNickname(), member.getId(), accessToken, refreshToken, member.getProfileUrl(),
                        true, member.getIsPushAlarmAllowed(), member.getMemberFanTeam(), member.getMemberLckYears(),
                        memberLevel, memberFanTeamLogo);

            }
            else {

                Boolean isDeleted = memberRepository.findMemberBySocialId(socialData.getId()).isDeleted();

                //재가입 방지
                if(isExistUser && isDeleted){
                    throw new BadRequestException(ErrorStatus.WITHDRAWAL_MEMBER.getMessage());
                }

                findMemberBySocialId(socialData.getId()).updateRefreshToken(refreshToken);

                // socialId를 통해서 등록된 유저 찾기
                Member signedMember = findMemberBySocialId(socialData.getId());

                Authentication authentication = new UserAuthentication(signedMember.getId(), null, null);

                String accessToken = jwtTokenProvider.generateAccessToken(authentication);

                int signedMemberLevel = MemberUtil.refineMemberExpToLevel(signedMember.getMemberExp());

                //todo : 추후 팀 로고를 통해서 저장된 s3값 가져오는 함수 적용
                String signedMemberFanTeamLogo = "";

                return AuthResponseDto.of(signedMember.getNickname(), signedMember.getId(), accessToken,
                        refreshToken, signedMember.getProfileUrl(), false, signedMember.getIsPushAlarmAllowed(),
                        signedMember.getMemberFanTeam(), signedMember.getMemberLckYears(), signedMemberLevel, signedMemberFanTeamLogo);
            }
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ErrorStatus.ANOTHER_ACCESS_TOKEN.getMessage());
        }
    }

    @Override
    @Transactional
    public AuthTokenResponseDto getNewToken(String accessToken, String refreshToken) {
        return AuthTokenResponseDto.of(accessToken,refreshToken);
    }

    private Member findMemberBySocialId(String socialId) {
        return memberRepository.findBySocialId(socialId)
                .orElseThrow(() -> new BadRequestException(ErrorStatus.INVALID_MEMBER.getMessage()));
    }

    private boolean isMemberBySocialId(String socialId) {
        return memberRepository.existsBySocialId(socialId);
    }

    private SocialInfoDto getSocialData(SocialPlatform socialPlatform, String socialAccessToken, String userName) {

        switch (socialPlatform) {
            case KAKAO:
                return kakaoAuthService.login(socialAccessToken);
            case APPLE:
                return appleAuthService.login(socialAccessToken, userName);
            case WITHDRAW:
                return null;
            default:
                throw new IllegalArgumentException(ErrorStatus.ANOTHER_ACCESS_TOKEN.getMessage());
        }
    }
}
