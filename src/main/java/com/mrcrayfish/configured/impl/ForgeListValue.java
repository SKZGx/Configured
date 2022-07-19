package com.mrcrayfish.configured.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.ValueSpec;

public class ForgeListValue extends ForgeValue<List<?>>
{
    private final Function<List<?>, List<?>> converter;
	
	public ForgeListValue(ConfigValue<List<?>> configValue, ValueSpec valueSpec)
	{
		super(configValue, valueSpec);
        this.converter = this.createConverter(configValue);
	}
	
    @Nullable
    private Function<List<?>, List<?>> createConverter(ForgeConfigSpec.ConfigValue<List<?>> configValue)
    {
        List<?> original = configValue.get();
        if(original instanceof ArrayList)
        {
            return ArrayList::new;
        }
        else if(original instanceof LinkedList)
        {
            return LinkedList::new;
        }
        // TODO allow developers to hook custom list
        return null;
    }
    
    @Override
    public void set(List<?> value)
    {
        this.valueSpec.correct(value);
    	super.set(new ArrayList<>(value));
    }
	
	public Function<List<?>, List<?>> getConverter()
	{
		return this.converter;
	}
}