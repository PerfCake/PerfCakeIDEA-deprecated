package org.perfcake.idea.util;

import org.apache.log4j.Logger;
import org.perfcake.message.generator.AbstractMessageGenerator;
import org.perfcake.message.sender.AbstractSender;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by miron on 18.3.2014.
 */
public class PerfCakeClassProvider {

    /**
     * Finds all subclass's names in a jar file of the superclass
     * @param superclass
     * @param pckage package in superclass's jar to search
     * @return list of subclass's names
     * @throws PerfCakeClassProviderException when an error occures during the search
     */
    private String[] getSubclasses(Class<?> superclass, String pckage) throws PerfCakeClassProviderException {
        String pckgPath = pckage.replace('.', '/');
        //get URL of the superclass
        URL url = superclass.getResource("/" + pckgPath);
        if(url == null){
            throw new NullPointerException("Could not find superclass resource: " + superclass.getName());
        }
        //we dont support superclass that is not in jar file
        if(!url.toString().startsWith("jar:")){
            throw new UnsupportedOperationException("Superclass is not inside jar file");
        }
        //get file URL of jar file
        String path = url.getPath();
        String[] split = path.split("!");
        URL jar = null;
        try {
            jar = new URL(split[0]);
        } catch (MalformedURLException e) {
            throw new PerfCakeClassProviderException("Unexpected Error in URL construction from superclass", e);
        }

        //find subclasses
        List<String> subclasses = new ArrayList<>();

        ZipInputStream zip = null;
        try {
            zip = new ZipInputStream(jar.openStream());
        } catch (IOException e) {
            throw new PerfCakeClassProviderException("Could not open jar file URL " + jar.toString() + " to get " + superclass.getName() + " subclasses", e);
        }
        ZipEntry entry = null;
        try {
            while ((entry = zip.getNextEntry()) != null) {
                String entryName = entry.getName();
                if (entryName.startsWith(pckgPath) && entryName.endsWith(".class")) {
                    String clazzName = entryName.replace('/', '.').substring(0, entryName.length() - 6);
                    try {
                        //construct class
                        Class clazz = Class.forName(clazzName);
                        if(superclass.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())){
                            subclasses.add(clazz.getSimpleName());
                        }
                    } catch (ClassNotFoundException e) {
                        throw new PerfCakeClassProviderException("Could not get listed class from package", e);
                    }
                }
            }
        } catch (IOException e) {
            throw new PerfCakeClassProviderException("Error while getting classes from jar file", e);
        }
        return subclasses.toArray(new String[0]);
    }

    /**
     * Finds all sender class's names in perfcake library in package org.perfcake.message.sender
     * @return array of sender class names
     * @throws PerfCakeClassProviderException if an Exception occurs during the search
     */
    public String[] findSenders() throws PerfCakeClassProviderException{
        return getSubclasses(AbstractSender.class, "org.perfcake.message.sender");
    }

    /**
     * Finds all generator class's names in PerfCake library in package org.perfcake.message.generator
     * @return array of generator class names
     * @throws PerfCakeClassProviderException if an Exception occurs during the search
     */
    public String[] findGenerators() throws PerfCakeClassProviderException {
        return getSubclasses(AbstractMessageGenerator.class, "org.perfcake.message.generator");
    }
}
