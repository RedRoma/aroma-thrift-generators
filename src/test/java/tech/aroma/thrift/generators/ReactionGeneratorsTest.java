/*
 * Copyright 2017 RedRoma, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.aroma.thrift.generators;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tech.aroma.thrift.reactions.*;
import tech.sirwellington.alchemy.arguments.AlchemyAssertion;
import tech.sirwellington.alchemy.arguments.FailedAssertionException;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static tech.sirwellington.alchemy.arguments.Arguments.*;
import static tech.sirwellington.alchemy.arguments.assertions.Assertions.notNull;
import static tech.sirwellington.alchemy.arguments.assertions.BooleanAssertions.trueStatement;
import static tech.sirwellington.alchemy.arguments.assertions.CollectionAssertions.nonEmptyList;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.*;

/**
 * @author SirWellington
 */
@Repeat(50)
@RunWith(AlchemyTestRunner.class)
public class ReactionGeneratorsTest
{

    @Before
    public void setUp() throws Exception
    {

        setupData();
        setupMocks();
    }

    private void setupData() throws Exception
    {

    }

    private void setupMocks() throws Exception
    {

    }

    @Test
    public void testMatchers()
    {
        AlchemyGenerator<AromaMatcher> instance = ReactionGenerators.matchers();
        assertThat(instance, notNullValue());

        AromaMatcher matcher = instance.get();
        assertThat(matcher, notNullValue());
        assertThat(matcher.isSet(), is(true));

        checkMatcher(matcher);
    }

    @Test
    public void testActions()
    {
        AlchemyGenerator<AromaAction> instance = ReactionGenerators.actions();
        assertThat(instance, notNullValue());

        AromaAction action = instance.get();
        assertThat(action, notNullValue());

        checkAction(action);
    }

    @Test
    public void testReactions()
    {

        AlchemyGenerator<Reaction> instance = ReactionGenerators.reactions();
        assertThat(instance, notNullValue());

        Reaction reaction = instance.get();
        assertThat(reaction, notNullValue());

        assertThat(reaction.name, not(isEmptyString()));
        assertThat(reaction.actions, not(empty()));
        assertThat(reaction.matchers, not(empty()));

        reaction.actions.forEach(this::checkAction);
        reaction.matchers.forEach(this::checkMatcher);
    }

    private void checkMatcher(AromaMatcher matcher)
    {
        checkThat(matcher.isSet())
                .is(trueStatement());

        if (matcher.isSetApplicationIs())
        {
            MatcherApplicationIs applicationIs = matcher.getApplicationIs();
            checkThat(applicationIs).is(notNull());
            checkThat(applicationIs.appId).is(validUuid());
        }

        if (matcher.isSetApplicationIsNot())
        {
            MatcherApplicationIsNot applicationIsNot = matcher.getApplicationIsNot();
            checkThat(applicationIsNot).is(notNull());
            checkThat(applicationIsNot.appId).is(validUuid());
        }

        if (matcher.isSetBodyContains())
        {
            MatcherBodyContains bodyContains = matcher.getBodyContains();
            checkThat(bodyContains).is(notNull());
            checkThat(bodyContains.substring).is(nonEmptyString());
        }

        if (matcher.isSetBodyDoesNotContain())
        {
            MatcherBodyDoesNotContain bodyDoesNotContain = matcher.getBodyDoesNotContain();
            checkThat(bodyDoesNotContain).is(notNull());
            checkThat(bodyDoesNotContain.substring).is(nonEmptyString());

        }

        if (matcher.isSetBodyIs())
        {
            MatcherBodyIs bodyIs = matcher.getBodyIs();
            checkThat(bodyIs).is(notNull());
            checkThat(bodyIs.expectedBody).is(nonEmptyString());
        }

        if (matcher.isSetHostnameContains())
        {
            MatcherHostnameContains hostnameContains = matcher.getHostnameContains();
            checkThat(hostnameContains).is(notNull());
            checkThat(hostnameContains.substring).is(nonEmptyString());
        }

        if (matcher.isSetHostnameDoesNotContain())
        {
            MatcherHostnameDoesNotContain hostnameDoesNotContain = matcher.getHostnameDoesNotContain();
            checkThat(hostnameDoesNotContain).is(notNull());
            checkThat(hostnameDoesNotContain.substring).is(nonEmptyString());
        }

        if (matcher.isSetHostnameIs())
        {
            MatcherHostnameIs hostnameIs = matcher.getHostnameIs();
            checkThat(hostnameIs).is(notNull());
            checkThat(hostnameIs.expectedHostname).is(nonEmptyString());
        }

        if (matcher.isSetTitleContains())
        {
            MatcherTitleContains titleContains = matcher.getTitleContains();
            checkThat(titleContains).is(notNull());
            checkThat(titleContains.substring).is(nonEmptyString());
        }

        if (matcher.isSetTitleDoesNotContain())
        {
            MatcherTitleDoesNotContain titleDoesNotContain = matcher.getTitleDoesNotContain();
            checkThat(titleDoesNotContain).is(notNull());
            checkThat(titleDoesNotContain.substring).is(nonEmptyString());
        }

        if (matcher.isSetTitleIs())
        {
            MatcherTitleIs titleIs = matcher.getTitleIs();
            checkThat(titleIs).is(notNull());
            checkThat(titleIs.expectedTitle).is(nonEmptyString());
        }

        if (matcher.isSetTitleIsNot())
        {
            MatcherTitleIsNot titleIsNot = matcher.getTitleIsNot();
            checkThat(titleIsNot).is(notNull());
            checkThat(titleIsNot.title).is(nonEmptyString());
        }
    }

    private AlchemyAssertion<String> validUuid()
    {
        return string ->
        {
            try
            {
                UUID.fromString(string);
            }
            catch (Exception ex)
            {
                throw new FailedAssertionException("Not a valid UUId: " + string);
            }
        };
    }

    private void checkAction(AromaAction action)
    {
        checkThat(action).is(notNull());
        checkThat(action.isSet()).is(trueStatement());

        if (action.isSetDontStoreMessage())
        {
            ActionDontStoreMessage dontStoreMessage = action.getDontStoreMessage();
            checkThat(dontStoreMessage).is(notNull());
        }

        if (action.isSetForwardToSlackChannel())
        {
            ActionForwardToSlackChannel forwardToSlackChannel = action.getForwardToSlackChannel();
            checkThat(forwardToSlackChannel).is(notNull());
            checkThat(forwardToSlackChannel.slackChannel).is(nonEmptyString());
        }

        if (action.isSetForwardToSlackUser())
        {
            ActionForwardToSlackUser forwardToSlackUser = action.getForwardToSlackUser();
            checkThat(forwardToSlackUser).is(notNull());
            checkThat(forwardToSlackUser.slackUsername).is(nonEmptyString());
        }

        if (action.isSetForwardToGitter())
        {
            ActionForwardToGitter forwardToGitter = action.getForwardToGitter();
            checkThat(forwardToGitter).is(notNull());
            checkThat(forwardToGitter.gitterWebhookUrl).is(nonEmptyString());
        }

        if (action.isSetForwardToUsers())
        {
            ActionForwardToUsers forwardToUsers = action.getForwardToUsers();
            checkThat(forwardToUsers).is(notNull());
            checkThat(forwardToUsers.userIds).is(nonEmptyList());

            forwardToUsers.userIds.forEach(id -> checkThat(id).is(validUuid()));
        }

        if (action.isSetResponseWithMessage())
        {
            ActionRespondWithMessage respondWithMessage = action.getResponseWithMessage();
            checkThat(respondWithMessage).is(notNull());
            checkThat(respondWithMessage.messageToRespondWith).is(nonEmptyString());
        }

        if (action.isSetSkipInbox())
        {
            ActionSkipInbox skipInbox = action.getSkipInbox();
            checkThat(skipInbox).is(notNull());
        }
    }

}
