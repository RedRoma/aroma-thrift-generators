 /*
  * Copyright 2016 RedRoma.
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
import tech.aroma.thrift.reactions.AromaMatcher;
import tech.aroma.thrift.reactions.MatcherApplicationIs;
import tech.aroma.thrift.reactions.MatcherApplicationIsNot;
import tech.aroma.thrift.reactions.MatcherBodyContains;
import tech.aroma.thrift.reactions.MatcherBodyDoesNotContain;
import tech.aroma.thrift.reactions.MatcherBodyIs;
import tech.sirwellington.alchemy.arguments.AlchemyAssertion;
import tech.sirwellington.alchemy.arguments.FailedAssertionException;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.Assertions.notNull;
import static tech.sirwellington.alchemy.arguments.assertions.BooleanAssertions.trueStatement;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.nonEmptyString;

/**
 *
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
        
        if(matcher.isSetApplicationIsNot())
        {
            MatcherApplicationIsNot applicationIsNot = matcher.getApplicationIsNot();
            checkThat(applicationIsNot).is(notNull());
            checkThat(applicationIsNot.appId).is(validUuid());
        }
        
        if(matcher.isSetBodyContains())
        {
            MatcherBodyContains bodyContains = matcher.getBodyContains();
            checkThat(bodyContains).is(notNull());
            checkThat(bodyContains.substring).is(nonEmptyString());
        }
        
        if(matcher.isSetBodyDoesNotContain())
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
    
}