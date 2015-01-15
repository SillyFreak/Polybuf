/**
 * PolybufInput.scala
 *
 * Created on 18.05.2013
 */

package at.pria.koza.polybuf

import scala.collection.mutable

import at.pria.koza.polybuf.proto.Polybuf.Obj

/**
 * <p>
 * `PolybufInput`
 * </p>
 *
 * @version V1.0 18.05.2013
 * @author SillyFreak
 */
class PolybufInput(config: PolybufConfig) {
  private val objects = mutable.Map[Int, Object]()

  @throws[PolybufException]
  def readObject(obj: Obj): AnyRef = {
    val typeId = obj.getTypeId
    val id = obj.getId
    if (typeId == 0) {
      if (id == 0)
        null
      else
        objects.get(id) match {
          case Some(instance) => instance
          case None =>
            throw new PolybufException("Unknown object: " + obj.getClass().getName() + ":" + id)
        }
    } else {
      config.get(typeId) match {
        case Some(io) => {
          val instance: io.TT = io.initialize(this, obj)
          objects.put(id, instance)
          io.deserialize(this, obj, instance)
          instance
        }
        case None =>
          throw new PolybufException("No IO for type: " + typeId)
      }
    }
  }
}
