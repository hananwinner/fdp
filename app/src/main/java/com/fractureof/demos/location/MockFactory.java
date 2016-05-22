package com.fractureof.demos.location;

import com.fractureof.demos.location.logic.profile.SocialProfile;
import com.fractureof.demos.location.logic.profile.SocialProfileMock;

/**
 * Created by tyler on 19/05/2016.
 */
public class MockFactory {
    public static SocialProfile createMockSocialProfile(String id) {

                return new SocialProfileMock(
                        id,
                        "Joey", "", "Triviani", "Joey Triviani");

    }
}
