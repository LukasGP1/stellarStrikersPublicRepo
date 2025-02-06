package de.lulkas_.stellarstrikers.rendering.shader;

import com.jogamp.opengl.GL2;
import de.lulkas_.stellarstrikers.GameObjectHandler;
import de.lulkas_.stellarstrikers.menu.button.ColorSelectorButton;
import de.lulkas_.stellarstrikers.menu.button.SwitchButton;
import de.lulkas_.stellarstrikers.menu.display.ImageBarDisplay;
import de.lulkas_.stellarstrikers.menu.display.IntDisplay;
import de.lulkas_.stellarstrikers.menu.display.TemporaryDisplay;
import de.lulkas_.stellarstrikers.rendering.AtlasAssembler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UniformHandler {
    private static List<Uniform> uniforms = new ArrayList<>();
    private static GameObjectHandler gameObjectHandler;

    private static void addUniform(Uniform uniform) {
        uniforms.add(uniform);
    }

    public static void setUniforms(GL2 gl, int shaderProgram) {
        for (Uniform uniform : uniforms) {
            if(uniform.getValues().get(0) instanceof Float) {
                switch (uniform.getValueCount()) {
                    case 1 ->
                            gl.glUniform1f(gl.glGetUniformLocation(shaderProgram, uniform.getName()), ((Float) uniform.getValues().get(1)));
                    case 2 ->
                            gl.glUniform2f(gl.glGetUniformLocation(shaderProgram, uniform.getName()), ((Float) uniform.getValues().get(1)), ((Float) uniform.getValues().get(2)));
                    case 3 ->
                            gl.glUniform3f(gl.glGetUniformLocation(shaderProgram, uniform.getName()), ((Float) uniform.getValues().get(1)), ((Float) uniform.getValues().get(2)), ((Float) uniform.getValues().get(3)));
                    case 4 ->
                            gl.glUniform4f(gl.glGetUniformLocation(shaderProgram, uniform.getName()), ((Float) uniform.getValues().get(1)), ((Float) uniform.getValues().get(2)), ((Float) uniform.getValues().get(3)), ((Float) uniform.getValues().get(4)));
                    default -> throw new RuntimeException(new IllegalStateException("Invalid value count."));
                }
            } else {
                switch (uniform.getValueCount()) {
                    case 1 ->
                            gl.glUniform1i(gl.glGetUniformLocation(shaderProgram, uniform.getName()), ((Integer) uniform.getValues().get(1)));
                    case 2 ->
                            gl.glUniform2i(gl.glGetUniformLocation(shaderProgram, uniform.getName()), ((Integer) uniform.getValues().get(1)), ((Integer) uniform.getValues().get(2)));
                    case 3 ->
                            gl.glUniform3i(gl.glGetUniformLocation(shaderProgram, uniform.getName()), ((Integer) uniform.getValues().get(1)), ((Integer) uniform.getValues().get(2)), ((Integer) uniform.getValues().get(3)));
                    case 4 ->
                            gl.glUniform4i(gl.glGetUniformLocation(shaderProgram, uniform.getName()), ((Integer) uniform.getValues().get(1)), ((Integer) uniform.getValues().get(2)), ((Integer) uniform.getValues().get(3)), ((Integer) uniform.getValues().get(4)));
                    default -> throw new RuntimeException(new IllegalStateException("Invalid value count."));
                }
            }
        }
    }

    public static void addUniforms(GameObjectHandler gameObjectHandler) {
        UniformHandler.gameObjectHandler = gameObjectHandler;

        long startTime = System.currentTimeMillis();

        addUniform(new Uniform("time", 1, () -> List.of(0f, (((int) (System.currentTimeMillis() - startTime))) / 1000f)));
        addUniform(new Uniform("boss_ticks", 1, () -> {
            if(gameObjectHandler.enemyWaveHandler == null) {
                return List.of(0, 120);
            } else {
                return List.of(0, gameObjectHandler.enemyWaveHandler.getDisplayBossTicks());
            }
        }));
        addUniform(new Uniform("game_state", 1, () -> switch (gameObjectHandler.gameState) {
            case PLAYING -> List.of(0, 0);
            case WON_MENU -> List.of(0, 1);
            case LOST_MENU -> List.of(0, 2);
            case MAIN_MENU -> List.of(0, 3);
            case STARTING_GAME -> List.of(0, 4);
            case ENTER_MAIN_MENU -> List.of(0, 5);
            case FIRST_ENTER_MAIN_MENU -> List.of(0, 6);
            case ENTER_WON_MENU -> List.of(0, 7);
            case ENTER_LOST_MENU -> List.of(0, 8);
            case ENTER_SKILL_MENU -> List.of(0, 9);
            case SKILL_MENU -> List.of(0, 10);
            case CREDITS_MENU -> List.of(0, 11);
            case ENTER_CREDITS_MENU -> List.of(0, 12);
            case SKIN_MENU -> List.of(0, 13);
            case ENTER_SKIN_MENU -> List.of(0, 14);
            case ENTER_OPTIONS_MENU -> List.of(0, 15);
            case OPTIONS_MENU -> List.of(0, 16);
        }));
        addUniform(new Uniform("player_data", 4, () -> {
            if(gameObjectHandler.player == null) {
                return List.of(0f, 0f, 0f, 0f, 0f);
            } else {
                return List.of(0f, gameObjectHandler.player.getGameX() / 1000f, gameObjectHandler.player.getGameY() / 1000f, gameObjectHandler.player.getGameWidth() / 1000f, gameObjectHandler.player.getGameHeight() / 1000f);
            }
        }));
        addUniform(new Uniform("atlas_size", 2, () -> List.of(0, AtlasAssembler.ATLAS_WIDTH, AtlasAssembler.ATLAS_HEIGHT)));
        addUniform(new Uniform("player_skin", 1, () -> List.of(0, gameObjectHandler.playerAttributeHandler.getSkinDisplayed() + 1)));
        addUniform(new Uniform("pBullet0", 4, getPBulletSupplier(0)));
        addUniform(new Uniform("pBullet1", 4, getPBulletSupplier(1)));
        addUniform(new Uniform("pBullet2", 4, getPBulletSupplier(2)));
        addUniform(new Uniform("pBullet3", 4, getPBulletSupplier(3)));
        addUniform(new Uniform("pBullet4", 4, getPBulletSupplier(4)));
        addUniform(new Uniform("pBullet5", 4, getPBulletSupplier(5)));
        addUniform(new Uniform("pHealthState", 1, () -> {
            if(gameObjectHandler.player != null) {
                return List.of(0f, gameObjectHandler.player.getDamageOverlay());
            } else {
                return List.of(0f, 0f);
            }
        }));
        addUniform(new Uniform("powerUpData0", 4, getPowerUpDataSupplier(0)));
        addUniform(new Uniform("powerUpData1", 4, getPowerUpDataSupplier(1)));
        addUniform(new Uniform("powerUpData2", 4, getPowerUpDataSupplier(2)));
        addUniform(new Uniform("powerUpData3", 4, getPowerUpDataSupplier(3)));
        addUniform(new Uniform("powerUpData4", 4, getPowerUpDataSupplier(4)));
        addUniform(new Uniform("bossPosData", 4, () -> {
            if(gameObjectHandler.enemyWaveHandler != null) {
                if(!gameObjectHandler.enemyWaveHandler.bosses.isEmpty()) {
                    return gameObjectHandler.enemyWaveHandler.bosses.get(0).getPosData();
                } else {
                    return List.of(0f, 0f, 0f, 0f, 0f);
                }
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("bossMiscData", 2, () -> {
            if(gameObjectHandler.enemyWaveHandler != null) {
                if(!gameObjectHandler.enemyWaveHandler.bosses.isEmpty()) {
                    return gameObjectHandler.enemyWaveHandler.bosses.get(0).getMiscData();
                } else {
                    return List.of(0f, 0f, 0f);
                }
            } else {
                return List.of(0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("skinDisplayPosData", 4, () -> {
            if(gameObjectHandler.skinDisplay != null) {
                return gameObjectHandler.skinDisplay.getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("skinDisplayMiscData", 2, () -> {
            if(gameObjectHandler.skinDisplay != null) {
                return gameObjectHandler.skinDisplay.getMiscData();
            } else {
                return List.of(0, 0, 0);
            }
        }));
        addUniform(new Uniform("skinExclamationMarkPosData", 4, () -> {
            if(gameObjectHandler.skinDisplay != null) {
                return gameObjectHandler.skinDisplay.exclamationMark.getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("skinExclamationMarkMiscData", 1, () -> {
            if(gameObjectHandler.skinDisplay != null) {
                return gameObjectHandler.skinDisplay.exclamationMark.getMiscData();
            } else {
                return List.of(0, 0);
            }
        }));
        addUniform(new Uniform("bombPosData0", 4, getBombPosDataSupplier(0)));
        addUniform(new Uniform("bombPosData1", 4, getBombPosDataSupplier(1)));
        addUniform(new Uniform("bombPosData2", 4, getBombPosDataSupplier(2)));
        addUniform(new Uniform("bombPosData3", 4, getBombPosDataSupplier(3)));
        addUniform(new Uniform("bombPosData4", 4, getBombPosDataSupplier(4)));
        addUniform(new Uniform("bombPosData5", 4, getBombPosDataSupplier(5)));
        addUniform(new Uniform("bombPosData6", 4, getBombPosDataSupplier(6)));
        addUniform(new Uniform("bombMiscData0", 1, getBombMiscDataSupplier(0)));
        addUniform(new Uniform("bombMiscData1", 1, getBombMiscDataSupplier(1)));
        addUniform(new Uniform("bombMiscData2", 1, getBombMiscDataSupplier(2)));
        addUniform(new Uniform("bombMiscData3", 1, getBombMiscDataSupplier(3)));
        addUniform(new Uniform("bombMiscData4", 1, getBombMiscDataSupplier(4)));
        addUniform(new Uniform("bombMiscData5", 1, getBombMiscDataSupplier(5)));
        addUniform(new Uniform("bombMiscData6", 1, getBombMiscDataSupplier(6)));
        addUniform(new Uniform("enemyPosData0", 4, getEnemyPosDataSupplier(0)));
        addUniform(new Uniform("enemyPosData1", 4, getEnemyPosDataSupplier(1)));
        addUniform(new Uniform("enemyPosData2", 4, getEnemyPosDataSupplier(2)));
        addUniform(new Uniform("enemyPosData3", 4, getEnemyPosDataSupplier(3)));
        addUniform(new Uniform("enemyPosData4", 4, getEnemyPosDataSupplier(4)));
        addUniform(new Uniform("enemyMiscData0", 2, getEnemyMiscDataSupplier(0)));
        addUniform(new Uniform("enemyMiscData1", 2, getEnemyMiscDataSupplier(1)));
        addUniform(new Uniform("enemyMiscData2", 2, getEnemyMiscDataSupplier(2)));
        addUniform(new Uniform("enemyMiscData3", 2, getEnemyMiscDataSupplier(3)));
        addUniform(new Uniform("enemyMiscData4", 2, getEnemyMiscDataSupplier(4)));
        addUniform(new Uniform("eBulletData0", 4, getEBulletSupplier(0)));
        addUniform(new Uniform("eBulletData1", 4, getEBulletSupplier(1)));
        addUniform(new Uniform("eBulletData2", 4, getEBulletSupplier(2)));
        addUniform(new Uniform("eBulletData3", 4, getEBulletSupplier(3)));
        addUniform(new Uniform("eBulletData4", 4, getEBulletSupplier(4)));
        addUniform(new Uniform("skinMenuExclamationMarkPosData", 4, () -> {
            if(gameObjectHandler.mainMenu != null) {
                return gameObjectHandler.mainMenu.skinExclamationMark.getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("skinMenuExclamationMarkMiscData", 1, () -> {
            if(gameObjectHandler.mainMenu != null) {
                return gameObjectHandler.mainMenu.skinExclamationMark.getMiscData();
            } else {
                return List.of(0, 0);
            }
        }));
        addUniform(new Uniform("skillMenuExclamationMarkPosData", 4, () -> {
            if(gameObjectHandler.mainMenu != null) {
                return gameObjectHandler.mainMenu.skillExclamationMark.getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("skillMenuExclamationMarkMiscData", 1, () -> {
            if(gameObjectHandler.mainMenu != null) {
                return gameObjectHandler.mainMenu.skillExclamationMark.getMiscData();
            } else {
                return List.of(0, 0);
            }
        }));
        addUniform(new Uniform("playerBulletColor", 4, () -> {
            Color color = gameObjectHandler.playerOptionsHandler.getPlayerBulletColor();
            return List.of(0f, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        }));
        addUniform(new Uniform("enemyBulletColor", 4, () -> {
            Color color = gameObjectHandler.playerOptionsHandler.getEnemyBulletColor();
            return List.of(0f, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        }));
        addUniform(new Uniform("optionsBombColor", 4, () -> {
            Color color = gameObjectHandler.playerOptionsHandler.getBombColor();
            return List.of(0f, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        }));
        addUniform(new Uniform("detonatedBombColor", 4, () -> {
            Color color = gameObjectHandler.playerOptionsHandler.getDetonatedBombColor();
            return List.of(0f, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        }));
        addUniform(new Uniform("colorButtonPos0", 4, getColorButtonPosSupplier(0)));
        addUniform(new Uniform("colorButtonPos1", 4, getColorButtonPosSupplier(1)));
        addUniform(new Uniform("colorButtonPos2", 4, getColorButtonPosSupplier(2)));
        addUniform(new Uniform("colorButtonPos3", 4, getColorButtonPosSupplier(3)));
        addUniform(new Uniform("colorButtonMisc0", 4, getColorButtonMiscSupplier(0)));
        addUniform(new Uniform("colorButtonMisc1", 4, getColorButtonMiscSupplier(1)));
        addUniform(new Uniform("colorButtonMisc2", 4, getColorButtonMiscSupplier(2)));
        addUniform(new Uniform("colorButtonMisc3", 4, getColorButtonMiscSupplier(3)));
        addUniform(new Uniform("switchButtonPos0", 4, getSwitchButtonPosSupplier(0)));
        addUniform(new Uniform("switchButtonPos1", 4, getSwitchButtonPosSupplier(1)));
        addUniform(new Uniform("switchButtonMisc0", 1, getSwitchButtonMiscSupplier(0)));
        addUniform(new Uniform("switchButtonMisc1", 1, getSwitchButtonMiscSupplier(1)));
        addUniform(new Uniform("skillMenuDisplayPos", 4, () -> {
            if(gameObjectHandler.skillMenu != null) {
                return ((TemporaryDisplay) gameObjectHandler.skillMenu.displayHandler.displays.get(1)).getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("skillMenuDisplayMisc", 1, () -> {
            if(gameObjectHandler.skillMenu != null) {
                return ((TemporaryDisplay) gameObjectHandler.skillMenu.displayHandler.displays.get(1)).getMiscData();
            } else {
                return List.of(0, 0);
            }
        }));
        addUniform(new Uniform("healthDisplayPosData", 4, () -> {
            if(gameObjectHandler.gameMenu != null) {
                return ((ImageBarDisplay) gameObjectHandler.gameMenu.displayHandler.displays.get(1)).getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("healthDisplayMiscData", 1, () -> {
            if(gameObjectHandler.gameMenu != null) {
                return ((ImageBarDisplay) gameObjectHandler.gameMenu.displayHandler.displays.get(1)).getMiscData();
            } else {
                return List.of(0, 0);
            }
        }));
        addUniform(new Uniform("scoreDisplayPosData", 4, () -> {
            if(gameObjectHandler.mainMenu != null) {
                return ((IntDisplay) gameObjectHandler.mainMenu.displayHandler.displays.get(0)).getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("scoreDisplayMiscData", 1, () -> {
            if(gameObjectHandler.mainMenu != null) {
                return ((IntDisplay) gameObjectHandler.mainMenu.displayHandler.displays.get(0)).getMiscData();
            } else {
                return List.of(0, 0);
            }
        }));
        addUniform(new Uniform("gameScoreDisplayPosData", 4, () -> {
            if(gameObjectHandler.gameMenu != null) {
                return ((IntDisplay) gameObjectHandler.gameMenu.displayHandler.displays.get(0)).getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("gameScoreDisplayMiscData", 1, () -> {
            if(gameObjectHandler.gameMenu != null) {
                return ((IntDisplay) gameObjectHandler.gameMenu.displayHandler.displays.get(0)).getMiscData();
            } else {
                return List.of(0, 0);
            }
        }));
        addUniform(new Uniform("levelPointDisplayPosData", 4, () -> {
            if(gameObjectHandler.skillMenu != null) {
                return ((IntDisplay) gameObjectHandler.skillMenu.displayHandler.displays.get(0)).getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("levelPointDisplayMiscData", 1, () -> {
            if(gameObjectHandler.skillMenu != null) {
                return ((IntDisplay) gameObjectHandler.skillMenu.displayHandler.displays.get(0)).getMiscData();
            } else {
                return List.of(0, 0);
            }
        }));
        addUniform(new Uniform("wonScoreDisplayPosData", 4, () -> {
            if(gameObjectHandler.wonMenu != null) {
                return ((IntDisplay) gameObjectHandler.wonMenu.displayHandler.displays.get(0)).getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("wonScoreDisplayMiscData", 1, () -> {
            if(gameObjectHandler.wonMenu != null) {
                return ((IntDisplay) gameObjectHandler.wonMenu.displayHandler.displays.get(0)).getMiscData();
            } else {
                return List.of(0, 0);
            }
        }));
        addUniform(new Uniform("wonTimeDisplayPosData", 4, () -> {
            if(gameObjectHandler.wonMenu != null) {
                return ((IntDisplay) gameObjectHandler.wonMenu.displayHandler.displays.get(1)).getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("wonTimeDisplayMiscData", 1, () -> {
            if(gameObjectHandler.wonMenu != null) {
                return ((IntDisplay) gameObjectHandler.wonMenu.displayHandler.displays.get(1)).getMiscData();
            } else {
                return List.of(0, 0);
            }
        }));
        addUniform(new Uniform("levelCostDisplayPosData", 4, () -> {
            if(gameObjectHandler.mainMenu != null) {
                return ((IntDisplay) gameObjectHandler.mainMenu.displayHandler.displays.get(1)).getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("levelCostDisplayMiscData", 1, () -> {
            if(gameObjectHandler.mainMenu != null) {
                return ((IntDisplay) gameObjectHandler.mainMenu.displayHandler.displays.get(1)).getMiscData();
            } else {
                return List.of(0, 0);
            }
        }));
        addUniform(new Uniform("levelDisplayPosData", 4, () -> {
            if(gameObjectHandler.mainMenu != null) {
                return ((IntDisplay) gameObjectHandler.mainMenu.displayHandler.displays.get(2)).getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("levelDisplayMiscData", 1, () -> {
            if(gameObjectHandler.mainMenu != null) {
                return ((IntDisplay) gameObjectHandler.mainMenu.displayHandler.displays.get(2)).getMiscData();
            } else {
                return List.of(0, 0);
            }
        }));
        addUniform(new Uniform("mainWaveDisplayPosData", 4, () -> {
            if(gameObjectHandler.mainMenu != null) {
                return ((IntDisplay) gameObjectHandler.mainMenu.displayHandler.displays.get(3)).getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("mainWaveDisplayMiscData", 1, () -> {
            if(gameObjectHandler.mainMenu != null) {
                return ((IntDisplay) gameObjectHandler.mainMenu.displayHandler.displays.get(3)).getMiscData();
            } else {
                return List.of(0, 0);
            }
        }));
        addUniform(new Uniform("gameWaveDisplayPosData", 4, () -> {
            if(gameObjectHandler.gameMenu != null) {
                return ((IntDisplay) gameObjectHandler.gameMenu.displayHandler.displays.get(2)).getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("gameWaveDisplayMiscData", 1, () -> {
            if(gameObjectHandler.gameMenu != null) {
                return ((IntDisplay) gameObjectHandler.gameMenu.displayHandler.displays.get(2)).getMiscData();
            } else {
                return List.of(0, 0);
            }
        }));
        addUniform(new Uniform("skinReqDisplayPosData", 4, () -> {
            if(gameObjectHandler.skinMenu != null) {
                return ((IntDisplay) gameObjectHandler.skinMenu.displayHandler.displays.get(0)).getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        }));
        addUniform(new Uniform("skinReqDisplayMiscData", 1, () -> {
            if(gameObjectHandler.skinMenu != null) {
                return ((IntDisplay) gameObjectHandler.skinMenu.displayHandler.displays.get(0)).getMiscData();
            } else {
                return List.of(0, 0, 0);
            }
        }));
    }

    private static Supplier<List<?>> getPBulletSupplier(int index) {
        return () -> {
            if(gameObjectHandler.player != null) {
                if(gameObjectHandler.player.bullets.size() > index) {
                    return gameObjectHandler.player.bullets.get(index).getData();
                } else {
                    return List.of(0f, 0f, 0f, 0f, 0f);
                }
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        };
    }

    private static Supplier<List<?>> getEBulletSupplier(int index) {
        return () -> {
            if(gameObjectHandler.enemyWaveHandler != null) {
                if(gameObjectHandler.enemyWaveHandler.enemies.size() > index) {
                    if(!gameObjectHandler.enemyWaveHandler.enemies.get(index).bullets.isEmpty()) {
                        return gameObjectHandler.enemyWaveHandler.enemies.get(index).bullets.get(0).getData();
                    } else {
                        return List.of(0f, 0f, 0f, 0f, 0f);
                    }
                } else {
                    return List.of(0f, 0f, 0f, 0f, 0f);
                }
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        };
    }

    private static Supplier<List<?>> getPowerUpDataSupplier(int index) {
        return () -> {
            if(gameObjectHandler.powerUpHandler != null) {
                if(gameObjectHandler.powerUpHandler.powerUps.size() > index) {
                    return gameObjectHandler.powerUpHandler.powerUps.get(index).getData();
                } else {
                    return List.of(0f, -1f, 0f, 0f, 0f);
                }
            } else {
                return List.of(0f, -1f, 0f, 0f, 0f);
            }
        };
    }

    private static Supplier<List<?>> getBombPosDataSupplier(int index) {
        return () -> {
            if(gameObjectHandler.enemyWaveHandler != null) {
                if(!gameObjectHandler.enemyWaveHandler.bosses.isEmpty()) {
                    if(gameObjectHandler.enemyWaveHandler.bosses.get(0).bombs.size() > index) {
                        return gameObjectHandler.enemyWaveHandler.bosses.get(0).bombs.get(index).getPosData();
                    } else {
                        return List.of(0f, 0f, 0f, 0f, 0f);
                    }
                } else {
                    return List.of(0f, 0f, 0f, 0f, 0f);
                }
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        };
    }

    private static Supplier<List<?>> getBombMiscDataSupplier(int index) {
        return () -> {
            if(gameObjectHandler.enemyWaveHandler != null) {
                if(!gameObjectHandler.enemyWaveHandler.bosses.isEmpty()) {
                    if(gameObjectHandler.enemyWaveHandler.bosses.get(0).bombs.size() > index) {
                        return gameObjectHandler.enemyWaveHandler.bosses.get(0).bombs.get(index).getMiscData();
                    } else {
                        return List.of(0, 0);
                    }
                } else {
                    return List.of(0, 0);
                }
            } else {
                return List.of(0, 0);
            }
        };
    }

    private static Supplier<List<?>> getEnemyPosDataSupplier(int index) {
        return () -> {
            if(gameObjectHandler.enemyWaveHandler != null) {
                if(gameObjectHandler.enemyWaveHandler.enemies.size() > index) {
                    return gameObjectHandler.enemyWaveHandler.enemies.get(index).getPosData();
                } else {
                    return List.of(0f, 0f, 0f, 0f, 0f);
                }
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        };
    }

    private static Supplier<List<?>> getEnemyMiscDataSupplier(int index) {
        return () -> {
            if(gameObjectHandler.enemyWaveHandler != null) {
                if(gameObjectHandler.enemyWaveHandler.enemies.size() > index) {
                    return gameObjectHandler.enemyWaveHandler.enemies.get(index).getMiscData();
                } else {
                    return List.of(0, 0, 0);
                }
            } else {
                return List.of(0, 0, 0);
            }
        };
    }

    private static Supplier<List<?>> getColorButtonPosSupplier(int index) {
        return () -> {
            if(gameObjectHandler.optionsMenu != null) {
                return ((ColorSelectorButton) gameObjectHandler.optionsMenu.clickables.get(index + 3)).getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        };
    }

    private static Supplier<List<?>> getColorButtonMiscSupplier(int index) {
        return () -> {
            if(gameObjectHandler.optionsMenu != null) {
                return ((ColorSelectorButton) gameObjectHandler.optionsMenu.clickables.get(index + 3)).getMiscData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        };
    }

    private static Supplier<List<?>> getSwitchButtonPosSupplier(int index) {
        return () -> {
            if(gameObjectHandler.optionsMenu != null) {
                return ((SwitchButton) gameObjectHandler.optionsMenu.clickables.get(1 + index)).getPosData();
            } else {
                return List.of(0f, 0f, 0f, 0f, 0f);
            }
        };
    }

    private static Supplier<List<?>> getSwitchButtonMiscSupplier(int index) {
        return () -> {
            if(gameObjectHandler.optionsMenu != null) {
                return ((SwitchButton) gameObjectHandler.optionsMenu.clickables.get(1 + index)).getMiscData();
            } else {
                return List.of(0, 0);
            }
        };
    }
}
