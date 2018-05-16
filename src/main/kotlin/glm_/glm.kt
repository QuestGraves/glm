/**
 * Created bY GBarbieri on 06.10.2016.
 */

package glm_

import glm_.func.*
import glm_.func.common.*
import glm_.gtc.gtcMatrixInverse
import glm_.gtx.*
import glm_.quat.gtxQuaternion
import glm_.quat.quat_func

object glm :

        func_common,
        func_vector1_common,
        func_vector2_common,
        func_vector3_common,
        func_vector4_common,

        func_vector1_relational,
        func_vector2_relational,
        func_vector3_relational,
        func_vector4_relational,
        func_vectorBoolean_relational,

        func_matrix,

        func_exponential,
        func_geometric,
        func_trigonometric,

        matrix_transform,
        quat_func,

        matrix_interpolation,

        epsilon,

        componentWise,

        round,

        funcInteger,

        bitfield,

        colorSpace,

        gauss,

        noise,

        packing,
        func_packing,

        random,

        optimumPow,

        gradientPaint,

        gtcMatrixInverse,

        gtxFastTrigonometry,
        gtxInteger,
        gtxIntersect,
        gtxMatrixDecompose,
        gtxQuaternion,
        gtxRotateVector,
        gtxTexture,
        gtxVectorAngle,

        closestPointToLines {

    @JvmField
    val detail = Detail

    @JvmField
    val HPI = kotlin.math.PI / 2
    @JvmField
    val HPIf = kotlin.math.PI.f / 2

    @JvmField
    val PI = kotlin.math.PI
    @JvmField
    val PIf = kotlin.math.PI.f

    @JvmField
    val PI2 = kotlin.math.PI * 2
    @JvmField
    val PI2f = kotlin.math.PI.f * 2

    @JvmField
    val PI3over2f = kotlin.math.PI.f * 1.5f

    @JvmField
    val epsilonF = 1.1920928955078125e-7f
    @JvmField
    val epsilon = 2.2204460492503131e-16

    @JvmField
    val Ef = 2.71828182845904523536f

    val π = Math.PI
}

object Detail :
        noise,
        typeHalf,
        packing_detail,
        detail_matrixDecompose,
        detail_fastTrigonometry,
        detail_gtxInteger,
        detail_integer


class Java {

    companion object {
        @JvmField
        val glm = glm_.glm
    }
}

typealias uint = Int
typealias ulong = Long

val GLM_VERSION_MAJOR = 0
val GLM_VERSION_MINOR = 9
val GLM_VERSION_PATCH = 9
val GLM_VERSION_REVISION = 0
val GLM_VERSION_BUILD = 0
val GLM_VERSION = 990.0