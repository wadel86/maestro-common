package io.maestro.common.saga.instance;

import io.maestro.common.util.JsonMapper;

public class SagaSerializedData {
    private String className;
    private String json;

    public SagaSerializedData(String className, String json) {
        this.className = className;
        this.json = json;
    }

    public static <Data> SagaSerializedData serializeSagaData(Data sagaData) {
        return new SagaSerializedData(sagaData.getClass().getName(), JsonMapper.toJson(sagaData));
    }

    public <Data> Data deserializeSagaData() {
        Class clasz = null;
        try {
            clasz = SagaSerializedData.class.getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
        }
        return (Data)JsonMapper.fromJson(json, clasz);
    }

    public String getClassName() {
        return className;
    }

    public String getJson() {
        return json;
    }
}
