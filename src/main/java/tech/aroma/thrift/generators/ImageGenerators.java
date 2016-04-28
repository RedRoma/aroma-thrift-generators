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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sir.wellington.alchemy.collections.lists.Lists;
import tech.aroma.thrift.Image;
import tech.aroma.thrift.ImageType;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.arguments.FailedAssertionException;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;

import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.Assertions.notNull;

/**
 *
 * @author SirWellington
 */
@NonInstantiable
public final class ImageGenerators
{

    private final static Logger LOG = LoggerFactory.getLogger(ImageGenerators.class);

    ImageGenerators() throws IllegalAccessException
    {
        throw new IllegalAccessException("cannot instantiate");
    }

    public static AlchemyGenerator<Image> appIcons() 
    {
       final List<String> icons = Arrays.asList("App-Icon-1.png",
                                            "App-Icon-2.png",
                                            "App-Icon-3.png",
                                            "App-Icon-4.png",
                                            "App-Icon-5.png",
                                            "App-Icon-6.png",
                                            "App-Icon-7.png");
       return () ->
       {
            Image icon = new Image();
            
            String image = Lists.oneOf(icons);
            URL resource = getResource("images/icons/" + image);
            
            byte[] binary;
            try
            {
                binary = toByteArray(resource);
            }
            catch(IOException ex)
            {
                throw new FailedAssertionException("Could not load Icon: " + image);
            }
            
            icon.setImageType(ImageType.PNG)
                .setData(binary);
            
            return icon;
       };
       
    }
    
    public static AlchemyGenerator<Image> profileImages()
    {
        List<String> images = Arrays.asList("Male-1.png",
                                            "Male-2.png",
                                            "Male-3.png",
                                            "Male-4.png",
                                            "Male-5.png",
                                            "Male-6.png",
                                            "Male-7.png",
                                            "Female-1.png",
                                            "Female-2.png",
                                            "Female-3.png",
                                            "Female-4.png",
                                            "Female-5.png",
                                            "Female-6.png");
        
        return () ->
        {
            Image profileImage = new Image();
            
            String image = Lists.oneOf(images);
            URL resource = getResource("images/profiles/" + image);
           
            byte[] binary;
            try
            {
                binary = toByteArray(resource);
            }
            catch (IOException ex)
            {
                LOG.error("Failed to load Resource {}", resource, ex);
                throw new FailedAssertionException("Could not load profile image: " + resource);
            }
            
            profileImage.setImageType(ImageType.PNG)
                .setData(binary);
            
            return profileImage;
        };
    }

    private static URL getResource(String name)
    {
        ClassLoader classLoader = UserGenerators.class.getClassLoader();
        URL url = classLoader.getResource(name);

        checkThat(url)
            .usingMessage("Could not load URL Resource: " + name)
            .is(notNull());
        return url;
    }

    private static byte[] toByteArray(URL resource) throws IOException
    {
        int bufferSize = 8096;

        try (InputStream istream = resource.openStream();
             ByteArrayOutputStream ostream = new ByteArrayOutputStream(bufferSize))
        {
            byte[] buffer = new byte[bufferSize];

            int bytesRead = istream.read(buffer);
            while (bytesRead > 0)
            {
                ostream.write(buffer, 0, bytesRead);
                bytesRead = istream.read(buffer);
            }

            return ostream.toByteArray();
        }
        catch (IOException ex)
        {
            LOG.error("Exception loading resource {} as Binary", resource, ex);
            throw ex;
        }
    }
}
