/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Robot.Utils;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author chad
 */
public class EncoderPIDSource implements PIDSource {

    private Encoder encoder1;

    public EncoderPIDSource(Encoder encoder1) {
        this.encoder1 = encoder1;
    }

    public double pidGet() {
        double encoder1Distance = this.encoder1.getDistance();

        return encoder1Distance;
    }
}
