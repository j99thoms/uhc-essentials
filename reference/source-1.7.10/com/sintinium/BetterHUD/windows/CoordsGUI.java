package com.sintinium.BetterHUD.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.sintinium.BetterHUD.BetterHUD;

public class CoordsGUI extends GuiScreen {
	private static boolean keyStates[];
	private WindowManager WM;
	private BetterHUD BH;
	private Minecraft mc;
	private OptionMenu om;
	private boolean fullbright = false;
	private boolean shouldChange = false;
	private double gamma;

	private int x;
	private int y;
	private int lastX;
	private int lastY;
	private int dx = 0;
	private int dy = 0;
	private boolean mouseFree = false;
	private boolean grabbed = false;
	private DefaultWindow DW;
	private boolean moved = false;

	private int drag = 54;
	private int bright = 48;

	private int lastMove;
	private boolean firstDrag = false;
	private boolean on = false;
	private Colorizer color;
	private FileManager FM;
	private FileManager FM1;
	private ArrayList<Double> data;
	private ArrayList<Double> data1;
	private boolean pressed = false;
	
	public static boolean guiOpen = false;

	private boolean optionsMenu = false;

	public CoordsGUI(WindowManager WM, Minecraft mc, BetterHUD BH) {
		this.WM = WM;
		keyStates = new boolean[256];
		this.mc = mc;
		this.BH = BH;
		FM = new FileManager("Gamma", 1);
		
		FM1 = new FileManager("keys.txt", 2);
		data1 = FM1.getArray();
		if (data1.size() < 2) {
			data1.add((double) drag);
			data1.add((double) bright);
			FM1.setArray(data1);
			data1.clear();
		} else {
			this.drag = (int) (double) this.data1.get(0);
			this.bright = (int) (double) this.data1.get(1);
		}
		
		om = new OptionMenu(this.mc, this, this.BH);
		data = FM.getArray();
		if (data.size() < 1) {
			data.add((double) this.mc.gameSettings.gammaSetting);
			FM.setArray(data);
			data.clear();
		} else {
			this.gamma = this.data.get(0);
			this.mc.gameSettings.gammaSetting = (float) gamma;
		}
	}

	public void save() {
		data.clear();
		data.add(this.gamma);
		FM.setArray(data);
	}

	private String resetAll = "Reset All Windows";
	
	public void update() {
		checkKeys();

		if (color != null && on) {
			color.update();
		}

		if (this.mouseFree && !this.optionsMenu) {
			drag();
		}
		if(this.mouseFree && !this.optionsMenu && !on) {
			ScaledResolution var1 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
			String reset = "Options";
			BH.drawHUDRectWithBorder(var1.getScaledWidth() / 2 - this.mc.fontRenderer.getStringWidth(reset) / 2, var1.getScaledHeight() / 2 - 5, this.mc.fontRenderer.getStringWidth(reset) + 1, 10, 0, 0, 0, 255, 255, 255, 255, 255, .5f);
			this.mc.fontRenderer.drawStringWithShadow(reset, var1.getScaledWidth() / 2 - this.mc.fontRenderer.getStringWidth(reset) / 2 + 1, var1.getScaledHeight() / 2 - 4, 0xffffffff);
			
			BH.drawHUDRectWithBorder(var1.getScaledWidth() / 2 - this.mc.fontRenderer.getStringWidth(resetAll) / 2, var1.getScaledHeight() / 2 - 5 + 20, this.mc.fontRenderer.getStringWidth(resetAll) + 1, 10, 0, 0, 0, 255, 255, 255, 255, 255, .5f);
			this.mc.fontRenderer.drawStringWithShadow(resetAll, var1.getScaledWidth() / 2 - this.mc.fontRenderer.getStringWidth(resetAll) / 2 + 1, var1.getScaledHeight() / 2 - 4 + 20, 0xffffffff);
		}
		
		if (this.optionsMenu) {
			if(this.mc.currentScreen == null) {
				this.mc.displayGuiScreen(om);
			}
			om.render();
		}

		if (fullbright) {
			this.mc.gameSettings.gammaSetting = 2000;
		} else if (shouldChange) {
			this.mc.gameSettings.gammaSetting = (float) gamma;
			shouldChange = false;
			save();
		} else if (gamma != this.mc.gameSettings.gammaSetting) {
			gamma = this.mc.gameSettings.gammaSetting;
			save();
		}

	}

	public static boolean checkKey(int i) {
		if (Keyboard.isKeyDown(i) != keyStates[i]) {
			return keyStates[i] = !keyStates[i];
		} else {
			return false;
		}
	}

	public void checkKeys() {
		// this.optionsMenu = false;
		if (Keyboard.getEventKeyState()) {
			FM1 = new FileManager("keys.txt", 2);
			data1 = FM1.getArray();
			if (data1.size() < 2) {
				data1.add((double) drag);
				data1.add((double) bright);
				FM1.setArray(data1);
				data1.clear();
			} else {
				this.drag = (int) (double) this.data1.get(0);
				this.bright = (int) (double) this.data1.get(1);
			}
		}
		if (this.mc.currentScreen == null && !this.optionsMenu) {
			if (this.checkKey(drag)) {
				guiOpen = true;
				WM.showAll();
				this.mouseFree = true;
				this.mc.displayGuiScreen(this);
			} else if (this.checkKey(bright)) {
				fullbright = !fullbright;
				shouldChange = true;
			}
		}
		if (this.checkKey(Keyboard.KEY_ESCAPE)) {
			guiOpen = false;
			// this.mc.displayGuiScreen(null);
			this.optionsMenu = false;
			this.mouseFree = false;
			om.reset();
			WM.reset();
			on = false;
			lastX = 0;
			lastY = 0;
			dx = 0;
			dy = 0;
			grabbed = false;
			this.color = null;
		}
	}

	String options = "Options";

	public void drag() {
		x = Mouse.getX();
		y = Mouse.getY();
		x = Mouse.getEventX() * this.width / this.mc.displayWidth;
		y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
		dx = x - lastX;
		dy = y - lastY;
		lastX = x;
		lastY = y;

		ScaledResolution var1 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		int Twidth = var1.getScaledWidth();
		int Theight = var1.getScaledHeight();

		if (Mouse.isButtonDown(0) && (!pressed || grabbed) && !this.optionsMenu) {
			if (x >= Twidth / 2 - this.mc.fontRenderer.getStringWidth(options) / 2 && x <= Twidth / 2 + this.mc.fontRenderer.getStringWidth(options) / 2 && y >= Theight / 2 - 22 && y <= Theight / 2 + 22) {
				WM.reset();
			}

			if (!this.firstDrag) {
				this.lastMove = 0;
			}

			if (!grabbed) {
				for (int i = 0; i < WM.DW.size(); i++) {
					if (x >= WM.DW.get(i).getX() && x <= WM.DW.get(i).getX() + WM.DW.get(i).getWidth()) {
						if (y >= WM.DW.get(i).getY() && y <= WM.DW.get(i).getY() + WM.DW.get(i).getHeight()) {
							DW = WM.DW.get(i);
							grabbed = true;
							this.firstDrag = true;
							return;
						}
					} else if (x >= Twidth / 2 - this.mc.fontRenderer.getStringWidth(options) / 2 && x <= Twidth / 2 + this.mc.fontRenderer.getStringWidth(options) / 2 && y >= Theight / 2 - 5 && y <= Theight / 2 + 5 && !on) {
						this.mc.displayGuiScreen(null);
						this.optionsMenu = true;
						return;
					} else if(x >= Twidth / 2 - this.mc.fontRenderer.getStringWidth(this.resetAll) / 2 && x <= Twidth / 2 + this.mc.fontRenderer.getStringWidth(options) / 2 && y >= Theight / 2 - 5 + 20 && y <= Theight / 2 + 5 + 20 && !on) {
						WM.resetAllWindowsPositions();
					}
				}
			} else {
				this.lastMove += dx + dy;
				DW.setX(DW.getX() + dx);
				DW.setY(DW.getY() + dy);
				DW.save();
			}
			pressed = true;
		} else if (lastMove == 0 && this.firstDrag) {
			this.firstDrag = false;
			// this.mc.displayGuiScreen(this);
			if (DW.getName().equalsIgnoreCase("Coordinate")) {
				this.color = new Colorizer(BH, DW, mc);
				on = true;
			} else {
				DW.toggle();
				DW.save();
			}
		} else {
			this.firstDrag = false;
			lastX = x;
			lastY = y;
			grabbed = false;
		}
		if (!Mouse.isButtonDown(0)) {
			pressed = false;
		}

	}

	public void mouseClicked() {
	}

	public boolean doesGuiPauseGame() {
		return false;
	}
}
