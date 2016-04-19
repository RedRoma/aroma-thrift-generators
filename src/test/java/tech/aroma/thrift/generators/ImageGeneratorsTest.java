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

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tech.aroma.thrift.Image;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.DontRepeat;
import tech.sirwellington.alchemy.test.junit.runners.GenerateInteger;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.Assertions.notNull;
import static tech.sirwellington.alchemy.arguments.assertions.CollectionAssertions.collectionOfSize;
import static tech.sirwellington.alchemy.arguments.assertions.CollectionAssertions.nonEmptyList;
import static tech.sirwellington.alchemy.arguments.assertions.NumberAssertions.greaterThan;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.listOf;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.assertThrows;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateInteger.Type.RANGE;

/**
 *
 * @author SirWellington
 */
@Repeat(10)
@RunWith(AlchemyTestRunner.class)
public class ImageGeneratorsTest
{

    @GenerateInteger(value = RANGE, min = 5, max = 20)
    private int count;

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

    @DontRepeat
    @Test
    public void testCannotInstantiate() throws Exception
    {
        assertThrows(() -> new ImageGenerators())
            .isInstanceOf(IllegalAccessException.class);
    }

    @Test
    public void testProfileImages()
    {
        AlchemyGenerator<Image> generator = ImageGenerators.profileImages();
        assertThat(generator, notNullValue());

        Image image = generator.get();
        checkImage(image);

        List<Image> images = listOf(generator, count);

        checkThat(images)
            .is(nonEmptyList())
            .is(collectionOfSize(count));

        images.forEach(this::checkImage);
    }

    @Test
    public void testAppIcons()
    {
        AlchemyGenerator<Image> generator = ImageGenerators.appIcons();
        assertThat(generator, notNullValue());

        Image image = generator.get();
        checkImage(image);

        List<Image> images = listOf(generator, count);

        checkThat(images)
            .is(nonEmptyList())
            .is(collectionOfSize(count));

        images.forEach(this::checkImage);
    }

    private void checkImage(Image image)
    {
        checkThat(image).is(notNull());

        checkThat(image.data)
            .are(notNull());

        byte[] binary = image.data.array();
        checkThat(binary).is(notNull());
        checkThat(binary.length).is(greaterThan(0));
    }
}
