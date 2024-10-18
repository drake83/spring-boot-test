package org.example.aspectj.annotations;

public @interface IgnoreInputAttributeTable {
    public Class<?> sourceClass() ;
    public Class<?> mixinClass() ;
}
