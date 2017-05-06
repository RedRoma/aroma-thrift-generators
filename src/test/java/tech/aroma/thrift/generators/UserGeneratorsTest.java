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

import java.util.List;
import junit.framework.AssertionFailedError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tech.aroma.thrift.User;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.DontRepeat;
import tech.sirwellington.alchemy.test.junit.runners.GenerateInteger;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.Assertions.notNull;
import static tech.sirwellington.alchemy.arguments.assertions.CollectionAssertions.collectionOfSize;
import static tech.sirwellington.alchemy.arguments.assertions.CollectionAssertions.nonEmptyList;
import static tech.sirwellington.alchemy.arguments.assertions.CollectionAssertions.nonEmptySet;
import static tech.sirwellington.alchemy.arguments.assertions.PeopleAssertions.validEmailAddress;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.nonEmptyString;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.validUUID;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.listOf;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.assertThrows;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateInteger.Type.RANGE;

/**
 *
 * @author SirWellington
 */
@Repeat(100)
@RunWith(AlchemyTestRunner.class)
public class UserGeneratorsTest
{

    @GenerateInteger(value = RANGE, min = 5, max = 20)
    private int count;

    @Before
    public void setUp() throws Exception
    {

    }

    @DontRepeat
    @Test
    public void testCannotInstatiate()
    {
        assertThrows(() -> new UserGenerators());
    }

    @Repeat(10)
    @Test
    public void testUsersWithProfileImages()
    {
        AlchemyGenerator<User> generator = UserGenerators.usersWithProfileImages();
        assertThat(generator, notNullValue());

        User user = generator.get();
        checkUserWithProfileImage(user);

        List<User> users = listOf(generator, count);
        checkThat(users)
            .is(nonEmptyList())
            .is(collectionOfSize(count));

        users.forEach(this::checkUserWithProfileImage);
    }

    @Test
    public void testUsersWithoutProfileImages()
    {
        AlchemyGenerator<User> generator = UserGenerators.users();
        assertThat(generator, notNullValue());

        User user = generator.get();
        checkUserWithoutProfileImage(user);

        List<User> users = listOf(generator, count);
        checkThat(users)
            .is(nonEmptyList())
            .is(collectionOfSize(count));

        users.forEach(this::checkUserWithoutProfileImage);
    }

    private void checkUserWithProfileImage(User user)
    {
        checkUserWithoutProfileImage(user);
        checkThat(user.profileImage)
            .throwing(AssertionFailedError.class)
            .is(notNull());
    }

    private void checkUserWithoutProfileImage(User user)
    {
        checkThat(user).is(notNull());
        checkThat(user.userId).is(validUUID());
        checkThat(user.name, user.firstName, user.middleName, user.lastName)
            .are(nonEmptyString());
        checkThat(user.roles).is(nonEmptySet());
        checkThat(user.email).is(validEmailAddress());
    }
}
