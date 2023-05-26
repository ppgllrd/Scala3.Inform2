/*
  Pepe Gallardo, 2022
 */

package inform.graphics.draw2D

import scala.annotation.targetName

class Transform extends java.awt.geom.AffineTransform:
  /**
   * Applies a transformation to a geometric shape.
   * @param shape to transform.
   * @return transformed shape.
   */
  def apply(shape: java.awt.Shape): java.awt.Shape =
    createTransformedShape(shape)

  
  override def toString: String =
    s"Transform([$getScaleX, $getShearX, $getTranslateX], [$getShearY, $getScaleY, $getTranslateY])"
  
  /**
   * Composes this and that transformations.
   * @param that second transformation to compose.
   * @return Composed transformation (this followed by that)
   */
  @targetName("andThen")
  def *(that: Transform): Transform =
    val result = this.clone.asInstanceOf[Transform]
    result.concatenate(that)
    result

  
  /**
   * Composes this and that transformations.
   * @param that second transformation to compose.
   * @return Composed transformation (this followed by that)
   */
  inline def andThen(that: Transform): Transform =
    this * that


object Transform:
  /**
   * Factory method for an identity transformation.
   * @return an identity transformation.
   */
  def apply(): Transform =
    new Transform()


  def unapply(t: Transform): (Double, Double, Double, Double, Double, Double) =
    (t.getScaleX, t.getShearX, t.getTranslateX, t.getShearY, t.getScaleY, t.getTranslateY)
  
  /**
   * Factory method for a rotation transformation.
   * @param alpha rotation in radians
   * @return a rotation transformation.
   */
  def rotate(alpha: Radian): Transform =
    val t = new Transform()
    t.rotate(alpha)
    t


  /**
   * Factory method for a translation transformation.
   * @param dx translation in X axis.
   * @param dy translation in Y axis.
   * @return a translation transformation.
   */
  def translate(dx: Double, dy: Double): Transform =
    val t = Transform()
    t.translate(dx, dy)
    t


  /**
   * Factory method for an scaling transformation.
   * @param sx scale in X axis.
   * @param sy scale in Y axis.
   * @return an scaling transformation.
   */
  def scale(sx: Double, sy: Double): Transform =
    val t = new Transform()
    t.scale(sx, sy)
    t


  /**
   * Factory method for a shearing transformation.
   * @param shx multiplier for X axis.
   * @param shy multiplier for Y axis.
   * @return a shearing transformation.
   */
  def shear(shx: Double, shy: Double): Transform =
    val t = new Transform()
    t.shear(shx, shy)
    t

