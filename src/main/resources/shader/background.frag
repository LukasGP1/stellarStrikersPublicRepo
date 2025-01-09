#version 120

varying vec2 fragPosition;

uniform float u_time;
uniform int spawnTicks;
uniform vec2 resolution;

float random(vec2 st) {
    return fract(fract(sin(dot(st.xy, vec2(12.9898, 78.233))) * 43758.5453123) * 48954.12594);
}

void main() {
    vec2 uv = fragPosition;

    float speed = 0.2;
    float offset = u_time * speed;
    uv.y += offset;

    float star = step(0.99, random(floor(uv * 200)));

    vec3 color = vec3(star);

    if(star == 0.) {
        color = vec3(0., 0., 0.18);
    }

    vec3 bossColor = vec3((120 - spawnTicks) / 480., 0., 0.);

    color += bossColor;

    gl_FragColor = vec4(color, 1.);
}