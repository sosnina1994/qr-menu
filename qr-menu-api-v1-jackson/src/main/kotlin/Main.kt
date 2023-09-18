import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.json.JsonMapper

val apiV1Mapper = JsonMapper.builder().run {
    enable(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL)
    build()
}
