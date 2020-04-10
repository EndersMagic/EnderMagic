package ru.mousecray.endmagic.util.render.elix_x.ecomms.color;

import java.util.Objects;

/**
 * Immutable HSBA color-space independent color representation.
 *
 * @author Elix_x
 */
public final class HSBA {

	private final float h, s, b, a;

	public HSBA(float h, float s, float b, float a){
		float mh = h % 1;
		this.h = mh > 0 ? mh : 1 + mh;
		this.s = Math.min(Math.max(s, 0), 1);
		this.b = Math.min(Math.max(b, 0), 1);
		this.a = Math.min(Math.max(a, 0), 1);
	}

	public HSBA(float h, float s, float b){
		this(h, s, b, 1f);
	}

	public HSBA(){
		this(0, 0, 0);
	}

	/*
	 * Get-set
	 */

	public float getH(){
		return h;
	}

	public double getHRad(){
		return h * 2 * Math.PI;
	}

	public double getHDeg(){
		return h * 360;
	}

	public float getS(){
		return s;
	}

	public float getB(){
		return b;
	}

	public float getA(){
		return a;
	}

	public HSBA setH(float h){
		return new HSBA(h, s, b, a);
	}

	public HSBA setHRad(double rad){
		return setH((float) (rad / (2 * Math.PI)));
	}

	public HSBA setHDeg(double deg){
		return setH((float) (deg / 360));
	}

	public HSBA setS(float s){
		return new HSBA(h, s, b, a);
	}

	public HSBA setB(float b){
		return new HSBA(h, s, b, a);
	}

	public HSBA setA(float a){
		return new HSBA(h, s, b, a);
	}

	/*
	 * Other color defs
	 */

	public RGBA toRGBA(){
		float r, g, b;
		if(s == 0) r = g = b = this.b;
		else {
			float h6 = this.h * 6;
			if(h6 == 6) h6 = 0; // H must be < 1
			int hi = (int) Math.floor((double) h6); // Or ... hi =
			float c1 = this.b * (1 - s);
			float c2 = this.b * (1 - s * (h6 - hi));
			float c3 = this.b * (1 - s * (1 - (h6 - hi)));

			if(hi == 0){
				r = this.b;
				g = c3;
				b = c1;
			} else if(hi == 1){
				r = c2;
				g = this.b;
				b = c1;
			} else if(hi == 2){
				r = c1;
				g = this.b;
				b = c3;
			} else if(hi == 3){
				r = c1;
				g = c2;
				b = this.b;
			} else if(hi == 4){
				r = c3;
				g = c1;
				b = this.b;
			} else {
				r = this.b;
				g = c1;
				b = c2;
			}
		}
		return new RGBA(r, g, b, a);
	}

	/*
	 * EH2S
	 */

	public static final float DEFAULTEQUALSDELTA = 1E-5F;

	private static boolean deltaEquals(float a, float b, float delta){
		return Math.abs(a - b) < DEFAULTEQUALSDELTA;
	}

	public boolean equals(HSBA hsba, float delta){
		return deltaEquals(hsba.h, h, delta) && deltaEquals(hsba.s, s, delta) && deltaEquals(hsba.b, b, delta) && deltaEquals(hsba.a, a, delta);
	}

	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		return equals((HSBA) o, DEFAULTEQUALSDELTA);
	}

	@Override
	public int hashCode(){
		return Objects.hash(h, s, b, a);
	}

	@Override
	public String toString(){
		return "HSBA{" + h + ", " + s + ", " + b + ", " + a + '}';
	}

}
