package com.clussmanproductions.trafficcontrol.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.clussmanproductions.trafficcontrol.tileentity.CrossingGateGateTileEntity;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class CrossingGateGateGui extends GuiScreen {
	
	private GuiTextField length;
	private GuiTextField upperRotation;
	private GuiTextField lowerRotation;
	private GuiTextField delay;

	private CrossingGateGateTileEntity te;
	public CrossingGateGateGui(CrossingGateGateTileEntity te)
	{
		this.te = te;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		int horizontalCenter = width / 2;
		int verticalCenter = height / 2;
		
		length = new GuiTextField(COMP_IDS.LENGTH, fontRenderer, horizontalCenter - 50, verticalCenter - 70, 100, 20);
		length.setText(String.valueOf(te.getCrossingGateLength()));
		
		upperRotation = new GuiTextField(COMP_IDS.UPPER_ROTATION, fontRenderer, horizontalCenter - 50, verticalCenter - 40, 100, 20);
		upperRotation.setText(String.valueOf(te.getUpperRotationLimit()));
		
		lowerRotation = new GuiTextField(COMP_IDS.LOWER_ROTATION, fontRenderer, horizontalCenter - 50, verticalCenter - 10, 100, 20);
		lowerRotation.setText(String.valueOf(te.getLowerRotationLimit()));
		
		delay = new GuiTextField(COMP_IDS.DELAY, fontRenderer, horizontalCenter - 50, verticalCenter + 20, 100, 20);
		delay.setText(String.valueOf(te.getDelay()));
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		int horizontalCenter = width / 2;
		int verticalCenter = height / 2;
		
		fontRenderer.drawString("Gate Length:", length.x - fontRenderer.getStringWidth("Gate Length:") - 10, length.y + (length.height / 4), 0xFFFFFF);
		length.drawTextBox();
		
		fontRenderer.drawString("Upper Rotation:", upperRotation.x - fontRenderer.getStringWidth("Upper Rotation:") - 10, upperRotation.y + (upperRotation.height / 4), 0xFFFFFF);
		upperRotation.drawTextBox();
		
		fontRenderer.drawString("Lower Rotation:", lowerRotation.x - fontRenderer.getStringWidth("Lower Rotation:") - 10, lowerRotation.y + (lowerRotation.height / 4), 0xFFFFFF);
		lowerRotation.drawTextBox();
		
		fontRenderer.drawString("Activation Delay:", delay.x - fontRenderer.getStringWidth("Activation Delay:") - 10, delay.y + (delay.height / 4), 0xFFFFFF);
		delay.drawTextBox();
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		
		if (Character.isDigit(typedChar) ||
				keyCode == Keyboard.KEY_DELETE || 
				keyCode == Keyboard.KEY_BACK ||
				keyCode == Keyboard.KEY_LEFT ||
				keyCode == Keyboard.KEY_RIGHT ||
				keyCode == Keyboard.KEY_HOME ||
				keyCode == Keyboard.KEY_END)
		{
			length.textboxKeyTyped(typedChar, keyCode);
			upperRotation.textboxKeyTyped(typedChar, keyCode);
			lowerRotation.textboxKeyTyped(typedChar, keyCode);
			delay.textboxKeyTyped(typedChar, keyCode);
		}
		
		if (keyCode == Keyboard.KEY_MINUS)
		{
			upperRotation.textboxKeyTyped(typedChar, keyCode);
			lowerRotation.textboxKeyTyped(typedChar, keyCode);
		}
		
		// Check decimals
		if (typedChar == '.')
		{
			keyTypedDecimal(length, typedChar, keyCode);
			keyTypedDecimal(upperRotation, typedChar, keyCode);
			keyTypedDecimal(lowerRotation, typedChar, keyCode);
			keyTypedDecimal(delay, typedChar, keyCode);
		}
	}
	
	private void keyTypedDecimal(GuiTextField textBox, char typedChar, int keyCode)
	{
		if (!textBox.getText().contains("."))
		{
			textBox.textboxKeyTyped(typedChar, keyCode);
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		length.mouseClicked(mouseX, mouseY, mouseButton);
		upperRotation.mouseClicked(mouseX, mouseY, mouseButton);
		lowerRotation.mouseClicked(mouseX, mouseY, mouseButton);
		delay.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void onGuiClosed() {
		te.setCrossingGateLength(Float.parseFloat(length.getText()));
		te.setUpperRotationLimit(Float.parseFloat(upperRotation.getText()));
		te.setLowerRotationLimit(Float.parseFloat(lowerRotation.getText()));
		te.setDelay(Float.parseFloat(delay.getText()));
		te.performClientToServerSync();
	}
	
	public static class COMP_IDS
	{
		public static final int LENGTH = 1;
		public static final int UPPER_ROTATION = 2;
		public static final int LOWER_ROTATION = 3;
		public static final int DELAY = 4;
	}
}
