#version 120

attribute vec4 position;

varying vec2 fragPosition;

void main() {
    gl_Position = position;
    fragPosition = position.xy;
}