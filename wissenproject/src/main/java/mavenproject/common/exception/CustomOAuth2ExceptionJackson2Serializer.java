package mavenproject.common.exception;

import mavenproject.common.handler.CustomOAuth2Exception;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;

/**
 * @author jinghong
 */
public class CustomOAuth2ExceptionJackson2Serializer extends StdSerializer<CustomOAuth2Exception> {

    public CustomOAuth2ExceptionJackson2Serializer() {
        super(CustomOAuth2Exception.class);
    }

    @Override
    public void serialize(CustomOAuth2Exception value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("code", value.getHttpErrorCode());
        String errorMessage = value.getMessage();
        if (errorMessage != null) {
            errorMessage = HtmlUtils.htmlEscape(errorMessage);
        }
        jgen.writeStringField("msg", errorMessage);
        jgen.writeEndObject();
    }

}
