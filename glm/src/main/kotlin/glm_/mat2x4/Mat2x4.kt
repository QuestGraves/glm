package  glm_.mat2x4

import glm_.*
import glm_.vec4.Vec4
import glm_.vec4.Vec4t
import kool.floatBufferBig
import kool.pos
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.util.*

/**
 * Created by GBarbieri on 09.12.2016.
 */

class Mat2x4(var array: FloatArray) : Mat2x4t<Float>() {

    constructor(list: Iterable<*>, index: Int = 0) : this(FloatArray(8) { list.elementAt(index + it)!!.toFloat })

    // -- Accesses --

    override operator fun get(index: Int) = Vec4(index * 4, array)
    override operator fun get(column: Int, row: Int) = array[column * 4 + row]

    override operator fun set(column: Int, row: Int, value: Float) = array.set(column * 4 + row, value)

    override operator fun set(index: Int, value: Vec4t<out Number>) {
        array[index * 4] = value.x.f
        array[index * 4 + 1] = value.y.f
        array[index * 4 + 2] = value.z.f
        array[index * 4 + 2] = value.w.f
    }

    operator fun set(i: Int, v: Vec4) {
        v.to(array, i * 4)
    }


    fun toFloatArray(): FloatArray = to(FloatArray(length), 0)
    infix fun to(floats: FloatArray): FloatArray = to(floats, 0)
    fun to(floats: FloatArray, index: Int): FloatArray {
        System.arraycopy(array, 0, floats, index, length)
        return floats
    }

    override fun to(buf: ByteBuffer, offset: Int): ByteBuffer = buf
            .putFloat(offset + 0 * Float.BYTES, array[0])
            .putFloat(offset + 1 * Float.BYTES, array[1])
            .putFloat(offset + 2 * Float.BYTES, array[2])
            .putFloat(offset + 3 * Float.BYTES, array[3])
            .putFloat(offset + 4 * Float.BYTES, array[4])
            .putFloat(offset + 5 * Float.BYTES, array[5])
            .putFloat(offset + 6 * Float.BYTES, array[6])
            .putFloat(offset + 7 * Float.BYTES, array[7])


    fun toFloatBufferStack(): FloatBuffer = to(MemoryStack.stackGet().mallocFloat(length), 0)
    infix fun toFloatBuffer(stack: MemoryStack): FloatBuffer = to(stack.mallocFloat(length), 0)
    fun toFloatBuffer(): FloatBuffer = to(floatBufferBig(length), 0)
    infix fun to(buf: FloatBuffer): FloatBuffer = to(buf, buf.pos)

    fun to(buf: FloatBuffer, offset: Int): FloatBuffer {
        buf[offset + 0] = array[0]
        buf[offset + 1] = array[1]
        buf[offset + 2] = array[2]
        buf[offset + 3] = array[3]
        buf[offset + 4] = array[4]
        buf[offset + 5] = array[5]
        buf[offset + 6] = array[6]
        buf[offset + 7] = array[7]
        return buf
    }

    override var a0: Float
        get() = array[0]
        set(v) = array.set(0, v)
    override var a1: Float
        get() = array[1]
        set(v) = array.set(1, v)
    override var a2: Float
        get() = array[2]
        set(v) = array.set(2, v)
    override var a3: Float
        get() = array[3]
        set(v) = array.set(3, v)

    override var b0: Float
        get() = array[4]
        set(v) = array.set(4, v)
    override var b1: Float
        get() = array[5]
        set(v) = array.set(5, v)
    override var b2: Float
        get() = array[6]
        set(v) = array.set(6, v)
    override var b3: Float
        get() = array[7]
        set(v) = array.set(7, v)


    companion object {
        const val length = Mat2x4t.length
        @JvmField
        val size = length * Float.BYTES
    }

    override fun size() = size

    override fun equals(other: Any?) = other is Mat2x4 && Arrays.equals(array, other.array)

    override fun hashCode() = 31 * (31 * this[0].hashCode() + this[1].hashCode()) + this[2].hashCode()
}