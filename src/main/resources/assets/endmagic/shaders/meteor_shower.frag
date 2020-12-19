uniform float iTime;
uniform vec2 iResolution;
uniform float seed;

const float minX = -0.25;
const float maxX = 0.25;

float spark(float v){
    return ((1.0-abs(v/0.5))-0.5) * float(v>minX && v<maxX);
}

float rand(float v){
    return fract(sin(v)*43758.5453123);
}

void main()
{
    // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = gl_FragCoord.xy/iResolution.xy;
    float  nTime = min(iTime,100.0);

    float y = spark(uv.x-0.5-nTime + rand(uv.y+seed)*100.0);

    gl_FragColor = vec4(vec3(y),1.0);
}
