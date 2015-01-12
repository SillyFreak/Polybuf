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
  private val _objects: Map[Object, Obj] = new IdentityHashMap()
  private val _config: PolybufConfig = config
  private var _id: Int = 0

  @throws[PolybufException]
  def writeObject(o: Object): Obj = {
    if (!o.isInstanceOf[PolybufSerializable])
      throw new PolybufException("not serializable: " + o)

    val instance = o.asInstanceOf[PolybufSerializable]
    val typeId = instance.getTypeId()
    val io: PolybufIO[AnyRef] = _config.get(typeId)
    if (io == null)
      throw new PolybufException("No IO for type: " + typeId)

    val obj = Obj.newBuilder()

    //create a reference for future writeObject calls
    _id += 1
    obj.setId(_id);
    _objects.put(o, obj.build())

    //parse the actual object. allow objects to overwrite the type
    obj.setTypeId(typeId)
    io.serialize(this, instance, obj)

    obj.build()
  }
}
