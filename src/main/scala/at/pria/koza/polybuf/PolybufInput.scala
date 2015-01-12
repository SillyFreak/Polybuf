/**
 * PolybufInput.scala
 *
 * Created on 18.05.2013
 */

package at.pria.koza.polybuf

import java.util.HashMap
import java.util.Map

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
  private val _objects: Map[Int, Object] = new HashMap()
  private val _config: PolybufConfig = config

  @throws[PolybufException]
  def readObject(obj: Obj): Object = {
    val typeId = obj.getTypeId()
    if (typeId == 0) {
      val id = obj.getId()
      if (id == 0) {
        null
      } else {
        val instance = _objects.get(id)
        if (instance == null)
          throw new PolybufException("Unknown object: " + obj.getClass().getName() + ":" + id)
        instance
      }
    } else {
      val io: PolybufIO[AnyRef] = config.get(typeId)
      if (io == null)
        throw new PolybufException("No IO for type: " + typeId)
      val instance = io.initialize(this, obj)
      _objects.put(obj.getId(), instance)
      io.deserialize(this, obj, instance)

      instance
    }
  }
}
