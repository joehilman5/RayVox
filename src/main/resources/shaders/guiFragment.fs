#version 400 core

in vec2 passCoords;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main() {

    fragColor = texture(textureSampler, passCoords);

}