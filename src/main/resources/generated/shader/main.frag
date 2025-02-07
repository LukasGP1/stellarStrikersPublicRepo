#version 330 core

in vec2 fragPosition;
out vec4 FragColor;

uniform float time;
uniform int boss_ticks;
uniform int game_state;
uniform vec2 atlas_size;

uniform sampler2D tex0;

float random( in vec2 x ) {
    float xhash = cos( x.x * 37.0 );
    float yhash = cos( x.y * 57.0 );
    return fract( 415.92653 * ( xhash + yhash ) );
}

vec3 backGroundColor() {
    vec2 uv = fragPosition;

    float speed = 0.2;
    float offset = time * speed;
    uv.y += offset;

    float star = step(0.99, random(floor(uv * 200)));

    vec3 color = vec3(star);

    if(star == 0.) {
        color = vec3(0., 0., 0.18);
    }

    vec3 bossColor = vec3((120 - boss_ticks) / 480., 0., 0.);

    color += bossColor;

    return color;
}
vec4 textureFromAtlas(in vec2 texCoord, vec2 atlasCoords, vec2 textureSizeOnAtlas) {
    vec2 convertedTexCoord = texCoord;
    convertedTexCoord *= textureSizeOnAtlas;
    convertedTexCoord += atlasCoords;

    return texture(tex0, convertedTexCoord);
}

uniform int skinMenuExclamationMarkMiscData;
uniform int skillMenuExclamationMarkMiscData;
uniform vec4 skinMenuExclamationMarkPosData;
uniform vec4 skillMenuExclamationMarkPosData;

vec4 getSkinMenuExclamationMarkColor(in vec2 texCoord) {
    if(skinMenuExclamationMarkMiscData == 1.) {
        vec2 exclamationMarkTexCoord = texCoord;
        exclamationMarkTexCoord -= skinMenuExclamationMarkPosData.xy;
        exclamationMarkTexCoord *= 1. / skinMenuExclamationMarkPosData.zw;

        if(exclamationMarkTexCoord.x <= 1. && exclamationMarkTexCoord.x >= 0. && exclamationMarkTexCoord.y <= 1. && exclamationMarkTexCoord.y >= 0.) {
            vec4 exclamationMarkColor = vec4(0.);
exclamationMarkColor = textureFromAtlas(exclamationMarkTexCoord, vec2(0.5206,0.0), vec2(0.005,0.005));
            return exclamationMarkColor;
        } else {
            return vec4(0.);
        }
    } else {
        return vec4(0.);
    }
}

vec4 getSkillMenuExclamationMarkColor(in vec2 texCoord) {
    if(skillMenuExclamationMarkMiscData == 1.) {
        vec2 exclamationMarkTexCoord = texCoord;
        exclamationMarkTexCoord -= skillMenuExclamationMarkPosData.xy;
        exclamationMarkTexCoord *= 1. / skillMenuExclamationMarkPosData.zw;

        if(exclamationMarkTexCoord.x <= 1. && exclamationMarkTexCoord.x >= 0. && exclamationMarkTexCoord.y <= 1. && exclamationMarkTexCoord.y >= 0.) {
            vec4 exclamationMarkColor = vec4(0.);
exclamationMarkColor = textureFromAtlas(exclamationMarkTexCoord, vec2(0.5206,0.0), vec2(0.005,0.005));
            return exclamationMarkColor;
        } else {
            return vec4(0.);
        }
    } else {
        return vec4(0.);
    }
}

vec4 menuTextureColor(in vec2 texCoord) {
    vec4 color = vec4(0.);

    switch(game_state) {
        case 3:
color = textureFromAtlas(texCoord, vec2(0.7156,0.0), vec2(0.16,0.09));
        vec4 skinMenuExclamationMarkColor = getSkinMenuExclamationMarkColor(texCoord);
        if(skinMenuExclamationMarkColor.a != 0.) {
            color = skinMenuExclamationMarkColor;
        }
        vec4 skillMenuExclamationMarkColor = getSkillMenuExclamationMarkColor(texCoord);
        if(skillMenuExclamationMarkColor.a != 0.) {
            color = skillMenuExclamationMarkColor;
        }
        break;
        case 2:
color = textureFromAtlas(texCoord, vec2(0.5556,0.0), vec2(0.16,0.09));
            break;
        case 11:
color = textureFromAtlas(texCoord, vec2(0.0192,0.0), vec2(0.16,0.09));
            break;
        case 16:
color = textureFromAtlas(texCoord, vec2(0.0,0.1), vec2(0.16,0.09));
            break;
        case 10:
color = textureFromAtlas(texCoord, vec2(0.16,0.1), vec2(0.16,0.09));
            break;
        case 13:
color = textureFromAtlas(texCoord, vec2(0.32,0.1), vec2(0.16,0.09));
            break;
        case 1:
color = textureFromAtlas(texCoord, vec2(0.48,0.1), vec2(0.16,0.09));
            break;
        case 0:
color = textureFromAtlas(texCoord, vec2(0.3492,0.0), vec2(0.16,0.09));
            break;
    }

    return color;
}
uniform vec4 player_data;
uniform int player_skin;
uniform vec4 pBullet0;
uniform vec4 pBullet1;
uniform vec4 pBullet2;
uniform vec4 pBullet3;
uniform vec4 pBullet4;
uniform vec4 pBullet5;
uniform float pHealthState;
uniform vec4 playerBulletColor;

vec4 getPlayerTextureColor(in vec2 playerTexCoord) {
    if(game_state == 0) {

        if(playerTexCoord.x <= 1. && playerTexCoord.x >= 0. && playerTexCoord.y <= 1. && playerTexCoord.y >= 0.) {
            vec4 color = vec4(0.);
            switch(player_skin) {
                case 1:
color = textureFromAtlas(playerTexCoord, vec2(0.64,0.1), vec2(0.0032,0.0026));
                    break;
                case 2:
color = textureFromAtlas(playerTexCoord, vec2(0.6804,0.1), vec2(0.0024,0.0022));
                    break;
                case 3:
color = textureFromAtlas(playerTexCoord, vec2(0.6828,0.1), vec2(0.0034,0.0027));
                    break;
                case 4:
color = textureFromAtlas(playerTexCoord, vec2(0.6862,0.1), vec2(0.0026,0.0023));
                    break;
                case 5:
color = textureFromAtlas(playerTexCoord, vec2(0.6888,0.1), vec2(0.0028,0.0022));
                    break;
                case 6:
color = textureFromAtlas(playerTexCoord, vec2(0.6916,0.1), vec2(0.004,0.0022));
                    break;
                case 7:
color = textureFromAtlas(playerTexCoord, vec2(0.6956,0.1), vec2(0.004,0.002));
                    break;
                case 8:
color = textureFromAtlas(playerTexCoord, vec2(0.6996,0.1), vec2(0.0032,0.0022));
                    break;
                case 9:
color = textureFromAtlas(playerTexCoord, vec2(0.7028,0.1), vec2(0.0034,0.0029));
                    break;
                case 10:
color = textureFromAtlas(playerTexCoord, vec2(0.6432,0.1), vec2(0.004,0.0028));
                    break;
                case 11:
color = textureFromAtlas(playerTexCoord, vec2(0.6472,0.1), vec2(0.0026,0.0022));
                    break;
                case 12:
color = textureFromAtlas(playerTexCoord, vec2(0.6498,0.1), vec2(0.0036,0.0032));
                    break;
                case 13:
color = textureFromAtlas(playerTexCoord, vec2(0.6534,0.1), vec2(0.0052,0.003));
                    break;
                case 14:
color = textureFromAtlas(playerTexCoord, vec2(0.6586,0.1), vec2(0.0038,0.0025));
                    break;
                case 15:
color = textureFromAtlas(playerTexCoord, vec2(0.6624,0.1), vec2(0.0028,0.0023));
                    break;
                case 16:
color = textureFromAtlas(playerTexCoord, vec2(0.6652,0.1), vec2(0.0034,0.0024));
                    break;
                case 17:
color = textureFromAtlas(playerTexCoord, vec2(0.6686,0.1), vec2(0.0032,0.0027));
                    break;
                case 18:
color = textureFromAtlas(playerTexCoord, vec2(0.6718,0.1), vec2(0.0042,0.0027));
                    break;
                case 19:
color = textureFromAtlas(playerTexCoord, vec2(0.676,0.1), vec2(0.0044,0.0036));
                    break;
            }
            return color;
        } else {
            return vec4(0.);
        }
    } else {
        return vec4(0.);
    }
}

vec4 getPlayerBulletColor(in vec2 texCoord) {
    if(game_state == 0) {
        if(texCoord.x > pBullet0.x && texCoord.x < pBullet0.x + pBullet0.z && texCoord.y > pBullet0.y && texCoord.y < pBullet0.y + pBullet0.w) {
            return playerBulletColor;
        } else if(texCoord.x > pBullet1.x && texCoord.x < pBullet1.x + pBullet1.z && texCoord.y > pBullet1.y && texCoord.y < pBullet1.y + pBullet1.w) {
            return playerBulletColor;
        } else if(texCoord.x > pBullet2.x && texCoord.x < pBullet2.x + pBullet2.z && texCoord.y > pBullet2.y && texCoord.y < pBullet2.y + pBullet2.w) {
            return playerBulletColor;
        } else if(texCoord.x > pBullet3.x && texCoord.x < pBullet3.x + pBullet3.z && texCoord.y > pBullet3.y && texCoord.y < pBullet3.y + pBullet3.w) {
            return playerBulletColor;
        } else if(texCoord.x > pBullet4.x && texCoord.x < pBullet4.x + pBullet4.z && texCoord.y > pBullet4.y && texCoord.y < pBullet4.y + pBullet4.w) {
            return playerBulletColor;
        } else if(texCoord.x > pBullet5.x && texCoord.x < pBullet5.x + pBullet5.z && texCoord.y > pBullet5.y && texCoord.y < pBullet5.y + pBullet5.w) {
            return playerBulletColor;
        } else {
            return vec4(0.);
        }
    } else {
        return vec4(0.);
    }
}

vec4 getPlayerColor(in vec2 texCoord) {
    vec2 playerTexCoord = texCoord;
    playerTexCoord -= player_data.xy;
    playerTexCoord *= (1. / player_data.zw);
    vec4 textureColor = getPlayerTextureColor(playerTexCoord);
    if(textureColor.a == 0.) {
        return getPlayerBulletColor(texCoord);
    } else {
        if(random(playerTexCoord) > pHealthState) {
            return vec4(0., 0., 0., 1.);
        } else {
            return textureColor;
        }
    }
}
uniform vec4 powerUpData0;
uniform vec4 powerUpData1;
uniform vec4 powerUpData2;
uniform vec4 powerUpData3;
uniform vec4 powerUpData4;

vec4 getPowerUpColor(in vec2 texCoord) {
    if(game_state == 0.) {
        vec4 powerUpColor = vec4(0.);

        vec2 powerUpTexCoord0 = texCoord;
        powerUpTexCoord0 -= powerUpData0.xy;
        powerUpTexCoord0 *= 1. / vec2(powerUpData0.z, powerUpData0.z / 9. * 16.);

        vec2 powerUpTexCoord1 = texCoord;
        powerUpTexCoord1 -= powerUpData1.xy;
        powerUpTexCoord1 *= 1. / vec2(powerUpData1.z, powerUpData1.z / 9. * 16.);

        vec2 powerUpTexCoord2 = texCoord;
        powerUpTexCoord2 -= powerUpData2.xy;
        powerUpTexCoord2 *= 1. / vec2(powerUpData2.z, powerUpData2.z / 9. * 16.);

        vec2 powerUpTexCoord3 = texCoord;
        powerUpTexCoord3 -= powerUpData3.xy;
        powerUpTexCoord3 *= 1. / vec2(powerUpData3.z, powerUpData3.z / 9. * 16.);

        vec2 powerUpTexCoord4 = texCoord;
        powerUpTexCoord4 -= powerUpData4.xy;
        powerUpTexCoord4 *= 1. / vec2(powerUpData4.z, powerUpData4.z / 9. * 16.);

        if(powerUpTexCoord0.x >= 0. && powerUpTexCoord0.x <= 1. && powerUpTexCoord0.y >= 0. && powerUpTexCoord0.y <= 1.) {
            if(powerUpData0.w == 0) {
powerUpColor = textureFromAtlas(powerUpTexCoord0, vec2(0.7062,0.1), vec2(0.005,0.005));
            } else if(powerUpData0.w == .5) {
powerUpColor = textureFromAtlas(powerUpTexCoord0, vec2(0.7162,0.1), vec2(0.005,0.005));
            } else if(powerUpData0.w == 1.) {
powerUpColor = textureFromAtlas(powerUpTexCoord0, vec2(0.7112,0.1), vec2(0.005,0.005));
            }
        } else if(powerUpTexCoord1.x >= 0. && powerUpTexCoord1.x <= 1. && powerUpTexCoord1.y >= 0. && powerUpTexCoord1.y <= 1.) {
            if(powerUpData1.w == 0) {
powerUpColor = textureFromAtlas(powerUpTexCoord1, vec2(0.7062,0.1), vec2(0.005,0.005));
            } else if(powerUpData1.w == .5) {
powerUpColor = textureFromAtlas(powerUpTexCoord1, vec2(0.7162,0.1), vec2(0.005,0.005));
            } else if(powerUpData1.w == 1.) {
powerUpColor = textureFromAtlas(powerUpTexCoord1, vec2(0.7112,0.1), vec2(0.005,0.005));
            }
        } else if(powerUpTexCoord2.x >= 0. && powerUpTexCoord2.x <= 1. && powerUpTexCoord2.y >= 0. && powerUpTexCoord2.y <= 1.) {
            if(powerUpData2.w == 0) {
powerUpColor = textureFromAtlas(powerUpTexCoord2, vec2(0.7062,0.1), vec2(0.005,0.005));
            } else if(powerUpData2.w == .5) {
powerUpColor = textureFromAtlas(powerUpTexCoord2, vec2(0.7162,0.1), vec2(0.005,0.005));
            } else if(powerUpData2.w == 1.) {
powerUpColor = textureFromAtlas(powerUpTexCoord2, vec2(0.7112,0.1), vec2(0.005,0.005));
            }
        } else if(powerUpTexCoord3.x >= 0. && powerUpTexCoord3.x <= 1. && powerUpTexCoord3.y >= 0. && powerUpTexCoord3.y <= 1.) {
            if(powerUpData3.w == 0) {
powerUpColor = textureFromAtlas(powerUpTexCoord3, vec2(0.7062,0.1), vec2(0.005,0.005));
            } else if(powerUpData3.w == .5) {
powerUpColor = textureFromAtlas(powerUpTexCoord3, vec2(0.7162,0.1), vec2(0.005,0.005));
            } else if(powerUpData3.w == 1.) {
powerUpColor = textureFromAtlas(powerUpTexCoord3, vec2(0.7112,0.1), vec2(0.005,0.005));
            }
        } else if(powerUpTexCoord4.x >= 0. && powerUpTexCoord4.x <= 1. && powerUpTexCoord4.y >= 0. && powerUpTexCoord4.y <= 1.) {
            if(powerUpData4.w == 0) {
powerUpColor = textureFromAtlas(powerUpTexCoord4, vec2(0.7062,0.1), vec2(0.005,0.005));
            } else if(powerUpData4.w == .5) {
powerUpColor = textureFromAtlas(powerUpTexCoord4, vec2(0.7162,0.1), vec2(0.005,0.005));
            } else if(powerUpData4.w == 1.) {
powerUpColor = textureFromAtlas(powerUpTexCoord4, vec2(0.7112,0.1), vec2(0.005,0.005));
            }
        }

        return powerUpColor;
    } else {
        return vec4(0.);
    }
}
uniform vec4 bossPosData;
uniform vec2 bossMiscData;
uniform vec4 bombPosData0;
uniform vec4 bombPosData1;
uniform vec4 bombPosData2;
uniform vec4 bombPosData3;
uniform vec4 bombPosData4;
uniform vec4 bombPosData5;
uniform vec4 bombPosData6;
uniform int bombMiscData0;
uniform int bombMiscData1;
uniform int bombMiscData2;
uniform int bombMiscData3;
uniform int bombMiscData4;
uniform int bombMiscData5;
uniform int bombMiscData6;
uniform vec4 optionsBombColor;
uniform vec4 detonatedBombColor;

vec4 getBossTextureColor(in vec2 texCoord) {
    vec2 bossTexCoord = texCoord;
    bossTexCoord -= bossPosData.xy;
    bossTexCoord *= (1. / bossPosData.zw);
    if(bossTexCoord.x >= 0. && bossTexCoord.x <= 1. && bossTexCoord.y >= 0. && bossTexCoord.y <= 1.) {
        vec4 bossColor = vec4(0.);
        if(bossMiscData.x == 0.) {
bossColor = textureFromAtlas(bossTexCoord, vec2(0.0096,0.0), vec2(0.0032,0.0026));
        } else if(bossMiscData.x == .5) {
bossColor = textureFromAtlas(bossTexCoord, vec2(0.016,0.0), vec2(0.0032,0.0026));
        } else if(bossMiscData.x == 1.) {
bossColor = textureFromAtlas(bossTexCoord, vec2(0.0128,0.0), vec2(0.0032,0.0026));
        }

        if(bossColor.a != 0.) {
            if(random(bossTexCoord) > bossMiscData.y) {
                bossColor = vec4(0., 0., 0., 1.);
            }
        }
        return bossColor;
    } else {
        return vec4(0.);
    }
}

vec4 getBombEllipseColor(in vec2 centerdUV, vec2 size, vec4 color, int detonated) {
    if(detonated == 1.) {
        vec2 uv = centerdUV / size;
        float dist = dot(uv, uv);
        if(dist <= 1.) {
            return color;
        } else {
            return vec4(0.);
        }
    } else {
        return vec4(0.);
    }
}

vec4 getBombColor(in vec2 texCoord) {
    vec4 bombColor = vec4(0.);

    vec2 bombTexCoord0 = texCoord;
    bombTexCoord0 -= bombPosData0.xy + vec2(bombPosData0.z / 16. * 3., bombPosData0.w / 16. * 3.);
    vec4 ellipseColor = getBombEllipseColor(bombTexCoord0, bombPosData0.zw, detonatedBombColor, bombMiscData0);
    if(ellipseColor.a != 0.) {
        bombColor = ellipseColor;
    }

    vec2 bombTexCoord1 = texCoord;
    bombTexCoord1 -= bombPosData1.xy + vec2(bombPosData1.z / 16. * 3., bombPosData1.w / 16. * 3.);
    ellipseColor = getBombEllipseColor(bombTexCoord1, bombPosData1.zw, detonatedBombColor, bombMiscData1);
    if(ellipseColor.a != 0.) {
        bombColor = ellipseColor;
    }

    vec2 bombTexCoord2 = texCoord;
    bombTexCoord2 -= bombPosData2.xy + vec2(bombPosData2.z / 16. * 3., bombPosData2.w / 16. * 3.);
    ellipseColor = getBombEllipseColor(bombTexCoord2, bombPosData2.zw, detonatedBombColor, bombMiscData2);
    if(ellipseColor.a != 0.) {
        bombColor = ellipseColor;
    }

    vec2 bombTexCoord3 = texCoord;
    bombTexCoord3 -= bombPosData3.xy + vec2(bombPosData3.z / 16. * 3., bombPosData3.w / 16. * 3.);
    ellipseColor = getBombEllipseColor(bombTexCoord3, bombPosData3.zw, detonatedBombColor, bombMiscData3);
    if(ellipseColor.a != 0.) {
        bombColor = ellipseColor;
    }

    vec2 bombTexCoord4 = texCoord;
    bombTexCoord4 -= bombPosData4.xy + vec2(bombPosData4.z / 16. * 3., bombPosData4.w / 16. * 3.);
    ellipseColor = getBombEllipseColor(bombTexCoord4, bombPosData4.zw, detonatedBombColor, bombMiscData4);
    if(ellipseColor.a != 0.) {
        bombColor = ellipseColor;
    }

    vec2 bombTexCoord5 = texCoord;
    bombTexCoord5 -= bombPosData5.xy + vec2(bombPosData5.z / 16. * 3., bombPosData5.w / 16. * 3.);
    ellipseColor = getBombEllipseColor(bombTexCoord5, bombPosData5.zw, detonatedBombColor, bombMiscData5);
    if(ellipseColor.a != 0.) {
        bombColor = ellipseColor;
    }

    vec2 bombTexCoord6 = texCoord;
    bombTexCoord6 -= bombPosData6.xy + vec2(bombPosData6.z / 16. * 3., bombPosData6.w / 16. * 3.);
    ellipseColor = getBombEllipseColor(bombTexCoord6, bombPosData6.zw, detonatedBombColor, bombMiscData6);
    if(ellipseColor.a != 0.) {
        bombColor = ellipseColor;
    }

    if(texCoord.x > bombPosData0.x && texCoord.y > bombPosData0.y && texCoord.x < bombPosData0.x + bombPosData0.z / 8. * 3. && texCoord.y < bombPosData0.y + bombPosData0.w / 8. * 3.) {
        bombColor = optionsBombColor;
    }

    if(texCoord.x > bombPosData1.x && texCoord.y > bombPosData1.y && texCoord.x < bombPosData1.x + bombPosData1.z / 8. * 3. && texCoord.y < bombPosData1.y + bombPosData1.w / 8. * 3.) {
        bombColor = optionsBombColor;
    }

    if(texCoord.x > bombPosData2.x && texCoord.y > bombPosData2.y && texCoord.x < bombPosData2.x + bombPosData2.z / 8. * 3. && texCoord.y < bombPosData2.y + bombPosData2.w / 8. * 3.) {
        bombColor = optionsBombColor;
    }

    if(texCoord.x > bombPosData3.x && texCoord.y > bombPosData3.y && texCoord.x < bombPosData3.x + bombPosData3.z / 8. * 3. && texCoord.y < bombPosData3.y + bombPosData3.w / 8. * 3.) {
        bombColor = optionsBombColor;
    }

    if(texCoord.x > bombPosData4.x && texCoord.y > bombPosData4.y && texCoord.x < bombPosData4.x + bombPosData4.z / 8. * 3. && texCoord.y < bombPosData4.y + bombPosData4.w / 8. * 3.) {
        bombColor = optionsBombColor;
    }

    if(texCoord.x > bombPosData5.x && texCoord.y > bombPosData5.y && texCoord.x < bombPosData5.x + bombPosData5.z / 8. * 3. && texCoord.y < bombPosData5.y + bombPosData5.w / 8. * 3.) {
        bombColor = optionsBombColor;
    }

    if(texCoord.x > bombPosData6.x && texCoord.y > bombPosData6.y && texCoord.x < bombPosData6.x + bombPosData6.z / 8. * 3. && texCoord.y < bombPosData6.y + bombPosData6.w / 8. * 3.) {
        bombColor = optionsBombColor;
    }
    return bombColor;
}

vec4 getBossColor(in vec2 texCoord) {
    if(game_state == 0){
        vec4 bossColor = getBombColor(texCoord);
        vec4 bossTextureColor = getBossTextureColor(texCoord);
        if(bossTextureColor.a != 0.) {
            bossColor = bossTextureColor;
        }
        return bossColor;
    } else {
        return vec4(0.);
    }
}
uniform vec4 skinDisplayPosData;
uniform ivec2 skinDisplayMiscData;
uniform vec4 skinExclamationMarkPosData;
uniform int skinExclamationMarkMiscData;

vec4 getSkinDisplayTextureColor(in vec2 texCoord) {
    vec2 skinDisplayTexCoord = texCoord;
    skinDisplayTexCoord -= skinDisplayPosData.xy;
    skinDisplayTexCoord *= 1. / skinDisplayPosData.zw;
    if(skinDisplayTexCoord.x >= 0. && skinDisplayTexCoord.x <= 1. && skinDisplayTexCoord.y >= 0. && skinDisplayTexCoord.y <= 1.) {
        vec4 skinDisplayColor = vec4(0.);
        switch(skinDisplayMiscData.x) {
            case 1:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.64,0.1), vec2(0.0032,0.0026));
            break;
            case 2:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6804,0.1), vec2(0.0024,0.0022));
            break;
            case 3:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6828,0.1), vec2(0.0034,0.0027));
            break;
            case 4:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6862,0.1), vec2(0.0026,0.0023));
            break;
            case 5:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6888,0.1), vec2(0.0028,0.0022));
            break;
            case 6:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6916,0.1), vec2(0.004,0.0022));
            break;
            case 7:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6956,0.1), vec2(0.004,0.002));
            break;
            case 8:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6996,0.1), vec2(0.0032,0.0022));
            break;
            case 9:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.7028,0.1), vec2(0.0034,0.0029));
            break;
            case 10:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6432,0.1), vec2(0.004,0.0028));
            break;
            case 11:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6472,0.1), vec2(0.0026,0.0022));
            break;
            case 12:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6498,0.1), vec2(0.0036,0.0032));
            break;
            case 13:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6534,0.1), vec2(0.0052,0.003));
            break;
            case 14:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6586,0.1), vec2(0.0038,0.0025));
            break;
            case 15:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6624,0.1), vec2(0.0028,0.0023));
            break;
            case 16:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6652,0.1), vec2(0.0034,0.0024));
            break;
            case 17:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6686,0.1), vec2(0.0032,0.0027));
            break;
            case 18:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.6718,0.1), vec2(0.0042,0.0027));
            break;
            case 19:
skinDisplayColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.676,0.1), vec2(0.0044,0.0036));
            break;

        }
        if(skinDisplayMiscData.y == 1.) {
            vec4 lockColor = vec4(0.);
lockColor = textureFromAtlas(skinDisplayTexCoord, vec2(0.5142,0.0), vec2(0.0064,0.0052));
            if(lockColor.a != 0.) {
                skinDisplayColor = lockColor;
            }
        }
        return skinDisplayColor;
    } else {
        return vec4(0.);
    }
}

vec4 getSkinExclamationMarkColor(in vec2 texCoord) {
    if(skinExclamationMarkMiscData == 1.) {
        vec2 exclamationMarkTexCoord = texCoord;
        exclamationMarkTexCoord -= skinExclamationMarkPosData.xy;
        exclamationMarkTexCoord *= 1. / skinExclamationMarkPosData.zw;

        if(exclamationMarkTexCoord.x <= 1. && exclamationMarkTexCoord.x >= 0. && exclamationMarkTexCoord.y <= 1. && exclamationMarkTexCoord.y >= 0.) {
            vec4 exclamationMarkColor = vec4(0.);
exclamationMarkColor = textureFromAtlas(exclamationMarkTexCoord, vec2(0.5206,0.0), vec2(0.005,0.005));
            return exclamationMarkColor;
        } else {
            return vec4(0.);
        }
    } else {
        return vec4(0.);
    }
}

vec4 getSkinDisplayColor(in vec2 texCoord) {
    if(game_state == 13) {
        vec4 skinDisplayColor = getSkinDisplayTextureColor(texCoord);
        vec4 exclamationMarkColor = getSkinExclamationMarkColor(texCoord);
        if(exclamationMarkColor.a != 0.) {
            skinDisplayColor = exclamationMarkColor;
        }
        return skinDisplayColor;
    } else {
        return vec4(0.);
    }
}
uniform vec4 enemyPosData0;
uniform vec4 enemyPosData1;
uniform vec4 enemyPosData2;
uniform vec4 enemyPosData3;
uniform vec4 enemyPosData4;
uniform ivec2 enemyMiscData0;
uniform ivec2 enemyMiscData1;
uniform ivec2 enemyMiscData2;
uniform ivec2 enemyMiscData3;
uniform ivec2 enemyMiscData4;
uniform vec4 eBulletData0;
uniform vec4 eBulletData1;
uniform vec4 eBulletData2;
uniform vec4 eBulletData3;
uniform vec4 eBulletData4;
uniform vec4 enemyBulletColor;

vec4 getEnemyTextureColor(in vec2 texCoord) {
    if(game_state == 0) {
        vec4 enemyColor = vec4(0.);

        vec2 enemyTexCoord0 = texCoord;
        enemyTexCoord0 -= enemyPosData0.xy;
        enemyTexCoord0 *= 1. / enemyPosData0.zw;
        if(enemyTexCoord0.x <= 1. && enemyTexCoord0.x >= 0. && enemyTexCoord0.y <= 1. && enemyTexCoord0.y >= 0.) {
            vec4 enemyColor0 = vec4(0.);
            switch(enemyMiscData0.x) {
                case 0:
enemyColor0 = textureFromAtlas(enemyTexCoord0, vec2(0.0096,0.0), vec2(0.0032,0.0026));
                break;
                case 1:
enemyColor0 = textureFromAtlas(enemyTexCoord0, vec2(0.016,0.0), vec2(0.0032,0.0026));
                break;
                case 2:
enemyColor0 = textureFromAtlas(enemyTexCoord0, vec2(0.0128,0.0), vec2(0.0032,0.0026));
                break;
            }
            if(enemyColor0.a != 0.) {
                if(random(enemyTexCoord0) > enemyMiscData0.y / 10.) {
                    enemyColor0 = vec4(0., 0., 0., 1.);
                }
                enemyColor = enemyColor0;
            }
        }

        vec2 enemyTexCoord1 = texCoord;
        enemyTexCoord1 -= enemyPosData1.xy;
        enemyTexCoord1 *= 1. / enemyPosData1.zw;
        if(enemyTexCoord1.x <= 1. && enemyTexCoord1.x >= 0. && enemyTexCoord1.y <= 1. && enemyTexCoord1.y >= 0.) {
            vec4 enemyColor1 = vec4(0.);
            switch(enemyMiscData1.x) {
                case 0:
enemyColor1 = textureFromAtlas(enemyTexCoord1, vec2(0.0096,0.0), vec2(0.0032,0.0026));
                break;
                case 1:
enemyColor1 = textureFromAtlas(enemyTexCoord1, vec2(0.016,0.0), vec2(0.0032,0.0026));
                break;
                case 2:
enemyColor1 = textureFromAtlas(enemyTexCoord1, vec2(0.0128,0.0), vec2(0.0032,0.0026));
                break;
            }
            if(enemyColor1.a != 0.) {
                if(random(enemyTexCoord1) > enemyMiscData1.y / 10.) {
                    enemyColor1 = vec4(0., 0., 0., 1.);
                }
                enemyColor = enemyColor1;
            }
        }

        vec2 enemyTexCoord2 = texCoord;
        enemyTexCoord2 -= enemyPosData2.xy;
        enemyTexCoord2 *= 1. / enemyPosData2.zw;
        if(enemyTexCoord2.x <= 1. && enemyTexCoord2.x >= 0. && enemyTexCoord2.y <= 1. && enemyTexCoord2.y >= 0.) {
            vec4 enemyColor2 = vec4(0.);
            switch(enemyMiscData2.x) {
                case 0:
enemyColor2 = textureFromAtlas(enemyTexCoord2, vec2(0.0096,0.0), vec2(0.0032,0.0026));
                break;
                case 1:
enemyColor2 = textureFromAtlas(enemyTexCoord2, vec2(0.016,0.0), vec2(0.0032,0.0026));
                break;
                case 2:
enemyColor2 = textureFromAtlas(enemyTexCoord2, vec2(0.0128,0.0), vec2(0.0032,0.0026));
                break;
            }
            if(enemyColor2.a != 0.) {
                if(random(enemyTexCoord2) > enemyMiscData2.y / 10.) {
                    enemyColor2 = vec4(0., 0., 0., 1.);
                }
                enemyColor = enemyColor2;
            }
        }

        vec2 enemyTexCoord3 = texCoord;
        enemyTexCoord3 -= enemyPosData3.xy;
        enemyTexCoord3 *= 1. / enemyPosData3.zw;
        if(enemyTexCoord3.x <= 1. && enemyTexCoord3.x >= 0. && enemyTexCoord3.y <= 1. && enemyTexCoord3.y >= 0.) {
            vec4 enemyColor3 = vec4(0.);
            switch(enemyMiscData3.x) {
                case 0:
enemyColor3 = textureFromAtlas(enemyTexCoord3, vec2(0.0096,0.0), vec2(0.0032,0.0026));
                break;
                case 1:
enemyColor3 = textureFromAtlas(enemyTexCoord3, vec2(0.016,0.0), vec2(0.0032,0.0026));
                break;
                case 2:
enemyColor3 = textureFromAtlas(enemyTexCoord3, vec2(0.0128,0.0), vec2(0.0032,0.0026));
                break;
            }
            if(enemyColor3.a != 0.) {
                if(random(enemyTexCoord3) > enemyMiscData3.y / 10.) {
                    enemyColor3 = vec4(0., 0., 0., 1.);
                }
                enemyColor = enemyColor3;
            }
        }

        vec2 enemyTexCoord4 = texCoord;
        enemyTexCoord4 -= enemyPosData4.xy;
        enemyTexCoord4 *= 1. / enemyPosData4.zw;
        if(enemyTexCoord4.x <= 1. && enemyTexCoord4.x >= 0. && enemyTexCoord4.y <= 1. && enemyTexCoord4.y >= 0.) {
            vec4 enemyColor4 = vec4(0.);
            switch(enemyMiscData4.x) {
                case 0:
enemyColor4 = textureFromAtlas(enemyTexCoord4, vec2(0.0096,0.0), vec2(0.0032,0.0026));
                break;
                case 1:
enemyColor4 = textureFromAtlas(enemyTexCoord4, vec2(0.016,0.0), vec2(0.0032,0.0026));
                break;
                case 2:
enemyColor4 = textureFromAtlas(enemyTexCoord4, vec2(0.0128,0.0), vec2(0.0032,0.0026));
                break;
            }
            if(enemyColor4.a != 0.) {
                if(random(enemyTexCoord4) > enemyMiscData4.y / 10.) {
                    enemyColor4 = vec4(0., 0., 0., 1.);
                }
                enemyColor = enemyColor4;
            }
        }

        return enemyColor;
    } else {
        return vec4(0.);
    }
}

vec4 getEnemyBulletColor(in vec2 texCoord) {
    if(game_state == 0) {
        if(texCoord.x > eBulletData0.x && texCoord.x < eBulletData0.x + eBulletData0.z && texCoord.y > eBulletData0.y && texCoord.y < eBulletData0.y + eBulletData0.w) {
            return enemyBulletColor;
        } else if(texCoord.x > eBulletData1.x && texCoord.x < eBulletData1.x + eBulletData1.z && texCoord.y > eBulletData1.y && texCoord.y < eBulletData1.y + eBulletData1.w) {
            return enemyBulletColor;
        } else if(texCoord.x > eBulletData2.x && texCoord.x < eBulletData2.x + eBulletData2.z && texCoord.y > eBulletData2.y && texCoord.y < eBulletData2.y + eBulletData2.w) {
            return enemyBulletColor;
        } else if(texCoord.x > eBulletData3.x && texCoord.x < eBulletData3.x + eBulletData3.z && texCoord.y > eBulletData3.y && texCoord.y < eBulletData3.y + eBulletData3.w) {
            return enemyBulletColor;
        } else if(texCoord.x > eBulletData4.x && texCoord.x < eBulletData4.x + eBulletData4.z && texCoord.y > eBulletData4.y && texCoord.y < eBulletData4.y + eBulletData4.w) {
            return enemyBulletColor;
        } else {
            return vec4(0.);
        }
    } else {
        return vec4(0.);
    }
}

vec4 getEnemyColor(in vec2 texCoord) {
    vec4 enemyColor = getEnemyBulletColor(texCoord);
    vec4 enemyTextureColor = getEnemyTextureColor(texCoord);
    if(enemyTextureColor.a != 0.) {
        enemyColor = enemyTextureColor;
    }
    return enemyColor;
}
uniform vec4 colorButtonPos0;
uniform vec4 colorButtonPos1;
uniform vec4 colorButtonPos2;
uniform vec4 colorButtonPos3;
uniform vec4 colorButtonMisc0;
uniform vec4 colorButtonMisc1;
uniform vec4 colorButtonMisc2;
uniform vec4 colorButtonMisc3;
uniform vec4 switchButtonPos0;
uniform vec4 switchButtonPos1;
uniform int switchButtonMisc0;
uniform int switchButtonMisc1;

vec4 getButtonColor(in vec2 texCoord) {
    if(game_state == 16) {
        vec4 buttonColor = vec4(0.);

        if(texCoord.x >= colorButtonPos0.x && texCoord.x <= colorButtonPos0.x + colorButtonPos0.z && texCoord.y >= colorButtonPos0.y && texCoord.y <= colorButtonPos0.y + colorButtonPos0.w) {
            if(colorButtonMisc0.a != 0.) {
                buttonColor = colorButtonMisc0;
            }
        }
        if(texCoord.x >= colorButtonPos1.x && texCoord.x <= colorButtonPos1.x + colorButtonPos1.z && texCoord.y >= colorButtonPos1.y && texCoord.y <= colorButtonPos1.y + colorButtonPos1.w) {
            if(colorButtonMisc1.a != 0.) {
                buttonColor = colorButtonMisc1;
            }
        }
        if(texCoord.x >= colorButtonPos2.x && texCoord.x <= colorButtonPos2.x + colorButtonPos2.z && texCoord.y >= colorButtonPos2.y && texCoord.y <= colorButtonPos2.y + colorButtonPos2.w) {
            if(colorButtonMisc2.a != 0.) {
                buttonColor = colorButtonMisc2;
            }
        }
        if(texCoord.x >= colorButtonPos3.x && texCoord.x <= colorButtonPos3.x + colorButtonPos3.z && texCoord.y >= colorButtonPos3.y && texCoord.y <= colorButtonPos3.y + colorButtonPos3.w) {
            if(colorButtonMisc3.a != 0.) {
                buttonColor = colorButtonMisc3;
            }
        }

        vec2 switchButtonTexCoord0 = texCoord;
        switchButtonTexCoord0 -= switchButtonPos0.xy;
        switchButtonTexCoord0 *= 1. / switchButtonPos0.zw;
        if(switchButtonTexCoord0.x <= 1. && switchButtonTexCoord0.x >= 0. && switchButtonTexCoord0.x <= 1. && switchButtonTexCoord0.x >= 0.) {
            vec4 switchButtonColor0 = vec4(0.);
            if(switchButtonMisc0 == 1) {
switchButtonColor0 = textureFromAtlas(switchButtonTexCoord0, vec2(0.5406,0.0), vec2(0.015,0.005));
            } else {
switchButtonColor0 = textureFromAtlas(switchButtonTexCoord0, vec2(0.5256,0.0), vec2(0.015,0.005));
            }
            if(switchButtonColor0.a != 0.) {
                buttonColor = switchButtonColor0;
            }
        }

        vec2 switchButtonTexCoord1 = texCoord;
        switchButtonTexCoord1 -= switchButtonPos1.xy;
        switchButtonTexCoord1 *= 1. / switchButtonPos1.zw;
        if(switchButtonTexCoord1.x <= 1. && switchButtonTexCoord1.x >= 0. && switchButtonTexCoord1.x <= 1. && switchButtonTexCoord1.x >= 0.) {
            vec4 switchButtonColor1 = vec4(0.);
            if(switchButtonMisc1 == 1) {
switchButtonColor1 = textureFromAtlas(switchButtonTexCoord1, vec2(0.5406,0.0), vec2(0.015,0.005));
            } else {
switchButtonColor1 = textureFromAtlas(switchButtonTexCoord1, vec2(0.5256,0.0), vec2(0.015,0.005));
            }
            if(switchButtonColor1.a != 0.) {
                buttonColor = switchButtonColor1;
            }
        }

        return buttonColor;
    } else {
        return vec4(0.);
    }
}
uniform vec4 skillMenuDisplayPos;
uniform int skillMenuDisplayMisc;
uniform vec4 healthDisplayPosData;
uniform int healthDisplayMiscData;
uniform vec4 scoreDisplayPosData;
uniform int scoreDisplayMiscData;
uniform vec4 gameScoreDisplayPosData;
uniform int gameScoreDisplayMiscData;
uniform vec4 levelPointDisplayPosData;
uniform int levelPointDisplayMiscData;
uniform vec4 wonScoreDisplayPosData;
uniform int wonScoreDisplayMiscData;
uniform vec4 wonTimeDisplayPosData;
uniform int wonTimeDisplayMiscData;
uniform vec4 levelCostDisplayPosData;
uniform int levelCostDisplayMiscData;
uniform vec4 levelDisplayPosData;
uniform int levelDisplayMiscData;
uniform vec4 mainWaveDisplayPosData;
uniform int mainWaveDisplayMiscData;
uniform vec4 gameWaveDisplayPosData;
uniform int gameWaveDisplayMiscData;
uniform vec4 skinReqDisplayPosData;
uniform int skinReqDisplayMiscData;
uniform vec4 fpsDisplayPosData;
uniform int fpsDisplayMiscData;

int getDigitCount(in int n) {
    if (n == 0) {
        return 1;
    }
    int count = 0;
    while (n > 0) {
        n /= 10;
        count++;
    }

    return count;
}

int getDigit(in int num, int index) {
    if (num == 0) {
        return index == 0 ? 0 : -1;
    }
    int numDigits = getDigitCount(num);
    if (index < 0 || index >= numDigits) {
        return -1;
    }
    int divisor = int(pow(10.0, float(numDigits - index - 1)));
    return (num / divisor) % 10;
}

vec4 getDigitColor(in vec2 texCoord, int digit) {
    if(digit < 0 || digit > 9) {
        return vec4(0.);
    } else {
        vec4 digitColor = vec4(0.);
        switch(digit) {
            case 0:
digitColor = textureFromAtlas(texCoord, vec2(0.1792,0.0), vec2(0.007,0.01));
            break;
            case 1:
digitColor = textureFromAtlas(texCoord, vec2(0.1862,0.0), vec2(0.007,0.01));
            break;
            case 2:
digitColor = textureFromAtlas(texCoord, vec2(0.1932,0.0), vec2(0.007,0.01));
            break;
            case 3:
digitColor = textureFromAtlas(texCoord, vec2(0.2002,0.0), vec2(0.007,0.01));
            break;
            case 4:
digitColor = textureFromAtlas(texCoord, vec2(0.2072,0.0), vec2(0.007,0.01));
            break;
            case 5:
digitColor = textureFromAtlas(texCoord, vec2(0.2142,0.0), vec2(0.007,0.01));
            break;
            case 6:
digitColor = textureFromAtlas(texCoord, vec2(0.2212,0.0), vec2(0.007,0.01));
            break;
            case 7:
digitColor = textureFromAtlas(texCoord, vec2(0.2282,0.0), vec2(0.007,0.01));
            break;
            case 8:
digitColor = textureFromAtlas(texCoord, vec2(0.2352,0.0), vec2(0.007,0.01));
            break;
            case 9:
digitColor = textureFromAtlas(texCoord, vec2(0.2422,0.0), vec2(0.007,0.01));
        }
        return digitColor;
    }
}

vec4 getIntDisplayColor(in int miscData, vec2 texCoord, vec4 posData) {
    if(getDigit(miscData, 0) == -1) {
        return vec4(0.);
    } else {
        vec2 displayTexCoord = texCoord;
        displayTexCoord -= posData.xy;
        displayTexCoord *= 1. / posData.zw;
        vec4 displayColor = vec4(0.);
        int i = 0;
        bool loop = true;
        while(loop) {
            int digit = getDigit(miscData, i);
            if(digit == -1) {
                loop = false;
            } else {
                if(displayTexCoord.x <= 1. && displayTexCoord.x >= 0. && displayTexCoord.y <= 1. && displayTexCoord.y >= 0.) {
                    vec4 digitColor = getDigitColor(displayTexCoord, digit);
                    if(digitColor.a != 0.) {
                        displayColor = digitColor;
                        loop = false;
                    }
                }
                displayTexCoord.x -= 1.;
                i++;
            }
        }
        return displayColor;
    }
}

vec4 getDisplayColor(in vec2 texCoord) {
    vec4 color = vec4(0.);

    vec4 fpsDisplayColor = getIntDisplayColor(fpsDisplayMiscData, texCoord, fpsDisplayPosData);
    if(fpsDisplayColor.a != 0.) {
        return fpsDisplayColor;
    }

    if(game_state == 10) {
        if(skillMenuDisplayMisc == 1.) {
            vec2 skillMenuDisplayTexCoord = texCoord;
            skillMenuDisplayTexCoord -= skillMenuDisplayPos.xy;
            skillMenuDisplayTexCoord *= 1. / skillMenuDisplayPos.zw;
            if(skillMenuDisplayTexCoord.x <= 1. && skillMenuDisplayTexCoord.x >= 0. && skillMenuDisplayTexCoord.y <= 1. && skillMenuDisplayTexCoord.y >= 0.) {
                vec4 skillMenuDisplayColor = vec4(0.);
skillMenuDisplayColor = textureFromAtlas(skillMenuDisplayTexCoord, vec2(0.2492,0.0), vec2(0.1,0.01));
                if(skillMenuDisplayColor.a != 0.) {
                    color = skillMenuDisplayColor;
                }
            }
        }

        vec4 levelPointDisplayColor = getIntDisplayColor(levelPointDisplayMiscData, texCoord, levelPointDisplayPosData);
        if(levelPointDisplayColor.a != 0.) {
            color = levelPointDisplayColor;
        }
    } else if(game_state == 0) {
        vec2 healthDisplayTexCoord = texCoord;
        healthDisplayTexCoord -= healthDisplayPosData.xy;
        healthDisplayTexCoord *= 1. / healthDisplayPosData.zw;
        vec4 healthDisplayColor = vec4(0.);
        for(int i = 0; i < healthDisplayMiscData; i++) {
            if(healthDisplayTexCoord.x <= 1. && healthDisplayTexCoord.x >= 0. && healthDisplayTexCoord.y <= 1. && healthDisplayTexCoord.y >= 0.) {
                vec4 heartColor = vec4(0.);
heartColor = textureFromAtlas(healthDisplayTexCoord, vec2(0.5092,0.0), vec2(0.005,0.005));
                if(heartColor.a != 0.) {
                    healthDisplayColor = heartColor;
                }
            }
            healthDisplayTexCoord.x -= 1.;
        }
        if(healthDisplayColor.a != 0.) {
            color = healthDisplayColor;
        }

        vec4 scoreDisplayColor = getIntDisplayColor(gameScoreDisplayMiscData, texCoord, gameScoreDisplayPosData);
        if(scoreDisplayColor.a != 0.) {
            color = scoreDisplayColor;
        }

        vec4 waveDisplayColor = getIntDisplayColor(gameWaveDisplayMiscData, texCoord, gameWaveDisplayPosData);
        if(waveDisplayColor.a != 0.) {
            color = waveDisplayColor;
        }
    } else if(game_state == 3) {
        vec4 scoreDisplayColor = getIntDisplayColor(scoreDisplayMiscData, texCoord, scoreDisplayPosData);
        if(scoreDisplayColor.a != 0.) {
            color = scoreDisplayColor;
        }

        vec4 levelCostDisplayColor = getIntDisplayColor(levelCostDisplayMiscData, texCoord, levelCostDisplayPosData);
        if(levelCostDisplayColor.a != 0.) {
            color = levelCostDisplayColor;
        }

        vec4 levelDisplayColor = getIntDisplayColor(levelDisplayMiscData, texCoord, levelDisplayPosData);
        if(levelDisplayColor.a != 0.) {
            color = levelDisplayColor;
        }

        vec4 waveDisplayColor = getIntDisplayColor(mainWaveDisplayMiscData, texCoord, mainWaveDisplayPosData);
        if(waveDisplayColor.a != 0.) {
            color = waveDisplayColor;
        }
    } else if(game_state == 1) {
        vec4 scoreDisplayColor = getIntDisplayColor(wonScoreDisplayMiscData, texCoord, wonScoreDisplayPosData);
        if(scoreDisplayColor.a != 0.) {
            color = scoreDisplayColor;
        }

        vec4 timeDisplayColor = getIntDisplayColor(wonTimeDisplayMiscData, texCoord, wonTimeDisplayPosData);
        if(timeDisplayColor.a != 0.) {
            color = timeDisplayColor;
        }
    } else if(game_state == 13) {
        vec4 skinReqDisplayColor = getIntDisplayColor(skinReqDisplayMiscData, texCoord, skinReqDisplayPosData);
        if(skinReqDisplayColor.a != 0.) {
            color = skinReqDisplayColor;
        }
    }

    return color;
}

void main() {
    vec2 texCoord = vec2(fragPosition.x, -fragPosition.y);
    texCoord = texCoord / 2. + .5;

    vec3 color = backGroundColor();

    vec4 enemyColor = getEnemyColor(texCoord);

    if(enemyColor.a != 0.) {
        color = enemyColor.rgb;
    }

    vec4 bossColor = getBossColor(texCoord);

    if(bossColor.a != 0.) {
        color = bossColor.rgb;
    }

    vec4 playerColor = getPlayerColor(texCoord);

    if(playerColor.a != 0.) {
        color = playerColor.rgb;
    }

    vec4 powerUpColor = getPowerUpColor(texCoord);

    if(powerUpColor.a != 0.) {
        color = powerUpColor.rgb;
    }

    vec4 menuTextureColor = menuTextureColor(texCoord);

    if(menuTextureColor.a != 0.) {
        color = menuTextureColor.rgb;
    }

    vec4 buttonColor = getButtonColor(texCoord);

    if(buttonColor.a != 0.) {
        color = buttonColor.rgb;
    }

    vec4 skinDisplayColor = getSkinDisplayColor(texCoord);

    if(skinDisplayColor.a != 0.) {
        color = skinDisplayColor.rgb;
    }

    vec4 displayColor = getDisplayColor(texCoord);

    if(displayColor.a != 0.) {
        color = displayColor.rgb;
    }

    FragColor = vec4(color, 1.0);
}
