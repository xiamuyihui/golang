package com.kingthy.conf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author:  潘勇
 * @Description:  数据库中的NULL自动转为默认值
 * @Date:  2017/6/23 14:44
 */
@Configuration
public class ConvertNull2DefaultConfig extends WebMvcConfigurerAdapter
{

    /**
     * @Author:  潘勇
     * @Description:  自定义JSON输入输出格式
     * @Date:  2017/6/23 14:44
     */
    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter()
    {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper om = new ObjectMapper();
        om.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);// 开启将输出没有JsonView注解的属性，false关闭将输出有JsonView注解的属性
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            false);// 配置该objectMapper在反序列化时，忽略目标对象没有的属性。凡是使用该objectMapper反序列化时，都会拥有该特性。
        om.setSerializerProvider(new CustomNullStringSerializerProvider());
        jsonConverter.setObjectMapper(om);
        return jsonConverter;
    }


    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        converters.add(customJackson2HttpMessageConverter());
    }

    /**
     * @Author:  潘勇
     * @Description:  自定义类型序列化工具，使null转成默认输出。
     * @Date:  2017/6/23 14:43
     */
    public static class CustomNullStringSerializerProvider extends DefaultSerializerProvider
    {
        private static final long serialVersionUID = 1L;

        public CustomNullStringSerializerProvider()
        {
            super();
        }

        public CustomNullStringSerializerProvider(CustomNullStringSerializerProvider provider,
            SerializationConfig config, SerializerFactory jsf)
        {
            super(provider, config, jsf);
        }

        @Override
        public CustomNullStringSerializerProvider createInstance(SerializationConfig config, SerializerFactory jsf)
        {
            return new CustomNullStringSerializerProvider(this, config, jsf);
        }

       /**
        * @Author:  潘勇
        * @Description:  处理指定类型中的NULL
        * @Date:  2017/6/23 14:43
        */
        @Override
        public JsonSerializer<Object> findNullValueSerializer(BeanProperty property)
            throws JsonMappingException
        {
            if (property.getType().getRawClass().equals(String.class)
                || property.getType().getRawClass().equals(Date.class))
            {
                return EmptyStringSerializer.INSTANCE;
            }
            else if (property.getType().getRawClass().equals(Integer.class))
            {
                return EmptyIntegerSerializer.INSTANCE;
            }
            else if (property.getType().getRawClass().equals(Double.class))
            {
                return EmptyDoubleSerializer.INSTANCE;
            }
            else if (property.getType().getRawClass().equals(BigDecimal.class))
            {
                return EmptyBigDecimalSerializer.INSTANCE;
            }
            else if (property.getType().getRawClass().equals(Boolean.class))
            {
                return EmptyBooleanSerializer.INSTANCE;
            }
            else if (property.getType().getRawClass().equals(List.class)
                || property.getType().getRawClass().equals(Set.class))
            {
                return EmptyArraySerializer.INSTANCE;
            }
            else
            {
                return super.findNullValueSerializer(property);
            }
        }
    }

    public static class EmptyBigDecimalSerializer extends JsonSerializer<Object>
    {
        public static final JsonSerializer<Object> INSTANCE = new EmptyBigDecimalSerializer();

        private EmptyBigDecimalSerializer()
        {
        }

        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException
        {
            jsonGenerator.writeNumber(BigDecimal.ZERO);
        }
    }

    public static class EmptyStringSerializer extends JsonSerializer<Object>
    {
        public static final JsonSerializer<Object> INSTANCE = new EmptyStringSerializer();

        private EmptyStringSerializer()
        {
        }

        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException
        {
            jsonGenerator.writeString("");
        }
    }

    public static class EmptyIntegerSerializer extends JsonSerializer<Object>
    {
        public static final JsonSerializer<Object> INSTANCE = new EmptyIntegerSerializer();

        private EmptyIntegerSerializer()
        {
        }

        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException
        {
            jsonGenerator.writeNumber(0);
        }
    }

    public static class EmptyDoubleSerializer extends JsonSerializer<Object>
    {
        public static final JsonSerializer<Object> INSTANCE = new EmptyDoubleSerializer();

        private EmptyDoubleSerializer()
        {
        }

        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException
        {
            jsonGenerator.writeNumber(0d);
        }
    }

    public static class EmptyBooleanSerializer extends JsonSerializer<Object>
    {
        public static final JsonSerializer<Object> INSTANCE = new EmptyBooleanSerializer();

        private EmptyBooleanSerializer()
        {
        }

        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException
        {
            jsonGenerator.writeBoolean(false);
        }
    }

    public static class EmptyArraySerializer extends JsonSerializer<Object>
    {
        public static final JsonSerializer<Object> INSTANCE = new EmptyArraySerializer();

        private EmptyArraySerializer()
        {
        }

        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException
        {
            int[] a = {};
            jsonGenerator.writeArray(a, 0, 0);
        }
    }
}