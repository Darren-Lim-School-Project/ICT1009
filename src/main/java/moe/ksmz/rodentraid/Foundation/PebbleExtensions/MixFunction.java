package moe.ksmz.rodentraid.Foundation.PebbleExtensions;

import com.mitchellbosecke.pebble.boot.autoconfigure.PebbleProperties;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class MixFunction implements Function {

    private final ResourceLoader resourceLoader;

    private final JsonParser jsonParser;

    private final PebbleProperties pebbleProperties;

    private Map<String, Object> cache;

    public MixFunction(ResourceLoader resourceLoader, PebbleProperties pebbleProperties) {
        this.resourceLoader = resourceLoader;
        this.pebbleProperties = pebbleProperties;
        this.jsonParser = JsonParserFactory.getJsonParser();
    }

    @Override
    public Object execute(
            Map<String, Object> map,
            PebbleTemplate pebbleTemplate,
            EvaluationContext evaluationContext,
            int i) {
        var key = (String) map.get("path");
        try {
            if (!pebbleProperties.isCache() || cache == null) {
                var manifestation =
                        resourceLoader.getResource("classpath:static/mix-manifest.json");
                var json =
                        StreamUtils.copyToString(
                                manifestation.getInputStream(), StandardCharsets.UTF_8);
                cache = jsonParser.parseMap(json);
            }

            if (resourceLoader.getResource("classpath:static/hot").exists()) {
                return "//localhost:8081" + cache.get(key);
            } else {
                return cache.get(key);
            }
        } catch (Exception e) {
            return key;
        }
    }

    @Override
    public List<String> getArgumentNames() {
        List<String> names = new ArrayList<>();
        names.add("path");
        return names;
    }
}
