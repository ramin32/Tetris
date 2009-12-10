package edu.cuny.brooklyn.tetris;
public class Velocity
{
    private final int constantVelocity_;
    private Integer temporaryVelocity_;

    public Velocity(int velocity)
    {
        constantVelocity_ = velocity;
    }

    public int getVelocity()
    {
        if(temporaryVelocity_ != null)
            return temporaryVelocity_;

        return constantVelocity_;
    }

    public void setTemporaryVelocity(Integer v)
    {
        temporaryVelocity_ = v;
    }


}
