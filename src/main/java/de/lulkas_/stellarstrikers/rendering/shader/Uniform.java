package de.lulkas_.stellarstrikers.rendering.shader;

import java.util.List;
import java.util.function.Supplier;

public class Uniform {
    private final String name;
    private final int valueCount;
    private final Supplier<List<?>> getValues;

    public Uniform(String name, int valueCount, Supplier<List<?>> getValues) {
        if(!(getValues.get().get(0) instanceof Float || getValues.get().get(0) instanceof Integer)) {
            throw new RuntimeException("Unsupported Uniform type");
        }
        this.name = name;
        this.valueCount = valueCount;
        this.getValues = getValues;
    }

    public String getName() {
        return name;
    }

    public int getValueCount() {
        return valueCount;
    }

    public List<?> getValues() {
        return getValues.get();
    }
}
