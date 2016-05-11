/*
 * Copyright 2016 RedRoma, Inc.
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

import java.time.Instant;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tech.aroma.thrift.Application;
import tech.aroma.thrift.ProgrammingLanguage;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.DontRepeat;
import tech.sirwellington.alchemy.test.junit.runners.GenerateInteger;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.Assertions.notNull;
import static tech.sirwellington.alchemy.arguments.assertions.CollectionAssertions.nonEmptySet;
import static tech.sirwellington.alchemy.arguments.assertions.NumberAssertions.greaterThan;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.nonEmptyString;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.validUUID;
import static tech.sirwellington.alchemy.arguments.assertions.TimeAssertions.inThePast;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.listOf;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.assertThrows;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateInteger.Type.RANGE;

/**
 *
 * @author SirWellington
 */
@Repeat(100)
@RunWith(AlchemyTestRunner.class)
public class ApplicationGeneratorsTest 
{

    @GenerateInteger(value = RANGE, min = 10, max = 100)
    private int size;
    
    @Before
    public void setUp() throws Exception
    {
    }
    
    @DontRepeat
    @Test
    public void testCannotInstantiate()
    {
        assertThrows(() -> new ApplicationGenerators());
    }


    @Test
    public void testPastTimes()
    {
        AlchemyGenerator<Long> generator = ApplicationGenerators.pastTimes();
        assertThat(generator, notNullValue());
        
        long result = generator.get();
        checkPastTime(result);
        
        for(int i = 0; i < size; ++i)
        {
            result = generator.get();
            checkPastTime(result);
        }
        
    }

    @Test
    public void testLanguages()
    {
        AlchemyGenerator<ProgrammingLanguage> generator = ApplicationGenerators.languages();
        assertThat(generator, notNullValue());
        
        ProgrammingLanguage result = generator.get();
        checkLanguage(result);
        
        List<ProgrammingLanguage> languages = listOf(generator, size);
        assertThat(languages, not(empty()));
        assertThat(languages.size(), is(size));
        
        languages.forEach(this::checkLanguage);
    }

    @Test
    public void testApplications()
    {
        AlchemyGenerator<Application> generator = ApplicationGenerators.applications();
        assertThat(generator, notNullValue());
        
        Application app = generator.get();
        checkApp(app);
        
        List<Application> apps = listOf(generator, size);
        assertThat(apps, not(empty()));
        assertThat(apps.size(), is(size));
        apps.forEach(this::checkApp);
    }

    private void checkPastTime(long time)
    {
        checkThat(Instant.ofEpochMilli(time))
            .is(inThePast());
    }

    private void checkLanguage(ProgrammingLanguage result)
    {
        assertThat(result, notNullValue());
    }


    @Repeat(10)
    @Test
    public void testApplicationsWithIcons()
    {
        AlchemyGenerator<Application> generator = ApplicationGenerators.applicationsWithIcons();
        assertThat(generator, notNullValue());
        
        Application app = generator.get();
        checkAppWithIcon(app);
        
        List<Application> apps = listOf(generator, size);
        assertThat(apps, not(empty()));
        assertThat(apps.size(), is(size));
        apps.forEach(this::checkAppWithIcon);
    }

    private void checkApp(Application app)
    {
        assertThat(app, notNullValue());
        checkPastTime(app.timeOfProvisioning);

        checkThat(app.applicationId).is(validUUID());
        checkThat(app.name).is(nonEmptyString());
        checkThat(app.owners).is(nonEmptySet());
        checkThat(app.applicationIconMediaId).is(validUUID());

    }
    
    
    private void checkAppWithIcon(Application app)
    {
        checkApp(app);
        
        checkThat(app.icon)
            .is(notNull());
        
        checkThat(app.icon.getData())
            .is(notNull());
        
        checkThat(app.icon.getData().length)
            .is(greaterThan(0));
    }
}