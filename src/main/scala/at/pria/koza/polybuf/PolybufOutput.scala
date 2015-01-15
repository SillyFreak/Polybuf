/**
 * PolybufOutput.scala
 *
 * Created on 18.05.2013
 */

package at.pria.koza.polybuf

import java.util.IdentityHashMap
import java.util.Map

import at.pria.koza.polybuf.proto.Polybuf.Obj

/**
 * <p>
 * `PolybufOutput`
 * </p>
 *
 * @version V1.0 18.05.2013
 * @author SillyFreak
 */
class PolybufOutput(config: PolybufConfig) {
  private val objects: Map[Object, Obj] = new IdentityHashMap()
  private var id: Int = 0

  @throws[PolybufException]
  def writeObject(o: Object): Obj = {
    if (!o.isInstanceOf[PolybufSerializable])
      throw new PolybufException("not serializable: " + o)

    val instance = o.asInstanceOf[PolybufSerializable]
    val typeId = instance.typeId
    config.get(typeId) match {
      case Some(io) => {
        val obj = Obj.newBuilder()

        //create a reference for future writeObject calls
        id += 1
        obj.setId(id);
        objects.put(o, obj.build())

        //parse the actual object. allow objects to overwrite the type
        obj.setTypeId(typeId)
        io.serialize(this, instance.asInstanceOf[io.TT], obj)

        obj.build()
      }
      case None =>
        throw new PolybufException("No IO for type: " + typeId)
    }
  }
}
