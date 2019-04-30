package ru.mousecray.endmagic.util.elix_x.ecomms.color;

import java.util.Objects;

/**
 * Immutable RGBA color-space independent color representation.
 *
 * @author Elix_x
 */
public final class RGBA {

	private final float r, g, b, a;

	public RGBA(float r, float g, float b, float a){
		this.r = Math.min(Math.max(r, 0), 1);
		this.g = Math.min(Math.max(g, 0), 1);
		this.b = Math.min(Math.max(b, 0), 1);
		this.a = Math.min(Math.max(a, 0), 1);
	}

	public RGBA(float r, float g, float b){
		this(r, g, b, 1f);
	}

	public RGBA(){
		this(0f, 0f, 0f);
	}

	public RGBA(int r, int g, int b, int a, int mv){
		this(r / (float) mv, g / (float) mv, b / (float) mv, a / (float) mv);
	}

	public RGBA(int r, int g, int b, int a){
		this(r, g, b, a, 255);
	}

	public RGBA(int r, int g, int b){
		this(r, g, b, 255);
	}

	/*
	 * Float
	 */

	public float getRF(){
		return r;
	}

	public float getGF(){
		return g;
	}

	public float getBF(){
		return b;
	}

	public float getAF(){
		return a;
	}

	public RGBA setRF(float r){
		return new RGBA(r, g, b, a);
	}

	public RGBA setGF(float g){
		return new RGBA(r, g, b, a);
	}

	public RGBA setBF(float b){
		return new RGBA(r, g, b, a);
	}

	public RGBA setAF(float a){
		return new RGBA(r, g, b, a);
	}

	/*
	 * Int
	 */

	public int getRI(int mv){
		return (int) (r * mv);
	}

	public int getGI(int mv){
		return (int) (g * mv);
	}

	public int getBI(int mv){
		return (int) (b * mv);
	}

	public int getAI(int mv){
		return (int) (a * mv);
	}

	public int getRI(){
		return getRI(255);
	}

	public int getGI(){
		return getGI(255);
	}

	public int getBI(){
		return getBI(255);
	}

	public int getAI(){
		return getAI(255);
	}

	public RGBA setRI(int r, int mv){
		return setRF(r / (float) mv);
	}

	public RGBA setGI(int g, int mv){
		return setGF(g / (float) mv);
	}

	public RGBA setBI(int b, int mv){
		return setBF(b / (float) mv);
	}

	public RGBA setAI(int a, int mv){
		return setAF(a / (float) mv);
	}

	public RGBA setRI(int r){
		return setRI(r, 255);
	}

	public RGBA setGI(int g){
		return setGI(g, 255);
	}

	public RGBA setBI(int b){
		return setBI(b, 255);
	}

	public RGBA setAI(int a){
		return setAI(a, 255);
	}

	/*
	 * Encoding
	 */

	public int argb(){
		return getAI() << 24 | getRI() << 16 | getGI() << 8 | getBI();
	}

	public static RGBA fromARGB(int argb){
		return new RGBA(argb >> 16 & 255, argb >> 8 & 255, argb & 255, argb >> 24 & 255);
	}

	public int rgba(){
		return getRI() << 24 | getGI() << 16 | getBI() << 8 | getAI();
	}

	public static RGBA fromRGBA(int argb){
		return new RGBA(argb >> 24 & 255, argb >> 16 & 255, argb >> 8 & 255, argb & 255);
	}

	/*
	 * Other color defs
	 */

	public HSBA toHSBA(){
		float max = Math.max(Math.max(r, g), b);
		float min = Math.min(Math.min(r, g), b);
		float d = max - min;
		float h;
		if(d == 0) h = 0;
		else if(r == max) h = (g - b) / d;
		else if(g == max) h = 2 + (b - r) / d;
		else h = 4 + (r - g) / d;
		return new HSBA(h / 6f, max != 0 ? d / max : 0, max, a);
	}

	/*
	 * EH2S
	 */

	public static final float DEFAULTEQUALSDELTA = 1E-5F;

	private static boolean deltaEquals(float a, float b, float delta){
		return Math.abs(a - b) < DEFAULTEQUALSDELTA;
	}

	public boolean equals(RGBA rgba, float delta){
		return deltaEquals(rgba.r, r, delta) && deltaEquals(rgba.g, g, delta) && deltaEquals(rgba.b, b, delta) && deltaEquals(rgba.a, a, delta);
	}

	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		return equals((RGBA) o, DEFAULTEQUALSDELTA);
	}

	@Override
	public int hashCode(){
		return Objects.hash(r, g, b, a);
	}

	@Override
	public String toString(){
		return "RGBA{ " + r + ", " + g + ", " + b + ", " + a + "}";
	}

}
