package com.anglefish.game.torus3d3;

public class CubicMotion {
    int mDuration;
    float mPath[];
    public float mTarget;
    long mDate;

    CubicMotion(int duration) {
        mDuration = duration > 0 ? duration : 1000;
        mDate = System.currentTimeMillis();
        mPath = new float[4];
        mTarget = 0;
    }

    void reset() {
        for (int i = 0; i < 4; i++) {
            mPath[i] = 0;
        }
        mTarget = 0;
    }

    public void setTarget(float targ) {
        float pos = this.getPosition();
        long x = System.currentTimeMillis() - this.mDate;
        float vel = 0;
        this.mDate += x;
        x /= this.mDuration;
        if (x >= 0 && x < 1) {
            vel = (3 * this.mPath[0] * x + 2 * this.mPath[1]) * x + this.mPath[2];
        }
        this.mTarget = targ;
        targ -= pos;
        mPath[0] = vel - 2 * targ;
        mPath[1] = 3 * targ - 2 * vel;
        mPath[2] = vel;
        mPath[3] = pos;
    }

    float getPosition() {
        long x = (System.currentTimeMillis() - this.mDate) / this.mDuration;
        if (x < 0 || x >= 1) {
            return this.mTarget;
        }
        return ((this.mPath[0] * x + this.mPath[1]) * x + this.mPath[2]) * x + this.mPath[3];
    }
}