package org.perfcake.idea.util;

import org.perfcake.message.generator.AbstractMessageGenerator;
import org.perfcake.message.sender.AbstractSender;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by miron on 18.3.2014.
 */
public class PerfCakeClassProvider {

    public Object[] findGenerators(){
        //Reflections reflections = new Reflections(ClasspathHelper.forPackage("org.perfcake.message.generator", ClasspathHelper.contextClassLoader()));
        Reflections reflections = new Reflections("org.perfcake.message.generator");
        Set<Class<? extends AbstractMessageGenerator>> subTypes = reflections.getSubTypesOf(AbstractMessageGenerator.class);
        Iterator<Class<? extends AbstractMessageGenerator>> iterator = subTypes.iterator();
        Set<String> generatorNames = new HashSet<>();
        while(iterator.hasNext()){
            generatorNames.add(iterator.next().getSimpleName());
        }
        return generatorNames.toArray();
    }

    public Object[] findSenders(){
        //Reflections reflections = new Reflections(ClasspathHelper.forPackage("org.perfcake.message.sender", ClasspathHelper.contextClassLoader()));
        Reflections reflections = new Reflections("org.perfcake.message.sender");
        Set<Class<? extends AbstractSender>> subTypes = reflections.getSubTypesOf(AbstractSender.class);
        Iterator<Class<? extends AbstractSender>> iterator = subTypes.iterator();
        Set<String> senderNames = new HashSet<>();
        while(iterator.hasNext()){
            senderNames.add(iterator.next().getSimpleName());
        }
        return senderNames.toArray();
    }
}
