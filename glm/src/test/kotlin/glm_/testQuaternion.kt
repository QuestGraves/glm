package glm_

import glm_.mat4x4.Mat4
import glm_.quat.Quat
import glm_.quat.QuatD
import glm_.quat.times
import glm_.vec3.Vec3
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

/**
 * Created by elect on 04/03/2017.
 */

class testQuaternion : StringSpec() {

    init {

        val epsilon = .0001f

        "quat angle" {

            run {

                val q = Quat().angleAxis(glm.πf * .25f, Vec3(0, 0, 1))
                val n = q.normalize()
                val l = n.length()

                glm.equal(l, 1f, .01f) shouldBe true

                val a = n.angle()

                glm.equal(a, glm.πf * .25f, .01f) shouldBe true
            }

            run {

                val q = Quat().angleAxis(glm.πf * .25f, Vec3(0, 1, 1).normalize())
                val n = q.normalize()
                val l = n.length()

                glm.equal(l, 1f, .01f) shouldBe true

                val a = n.angle()

                glm.equal(a, glm.πf * .25f, .01f) shouldBe true
            }

            run {

                val q = Quat().angleAxis(glm.πf * .25f, Vec3(1, 2, 3).normalize())
                val n = q.normalize()
                val l = n.length()

                glm.equal(l, 1f, .01f) shouldBe true

                val a = n.angle()

                glm.equal(a, glm.πf * .25f, .01f) shouldBe true
            }
        }

        "quat axis" {

            val a = Quat().angleAxis(0f, Vec3(0, 0, 1))
            val b = Quat().angleAxis(glm.πf * .5f, Vec3(0, 0, 1))
            val c = glm.mix(a, b, .5f, Quat())
            val d = Quat().angleAxis(glm.πf * .25f, Vec3(0, 0, 1))

            glm.all(glm.equal(c, d, .01f)) shouldBe true
        }

        "quat mix" {

            val a = Quat().angleAxis(0f, Vec3(0, 0, 1))
            val b = Quat().angleAxis(glm.πf * .5f, Vec3(0, 0, 1))
            val c = glm.mix(a, b, .5f, Quat())
            val d = Quat().angleAxis(glm.πf * .25f, Vec3(0, 0, 1))

            glm.all(glm.equal(c, d, .01f)) shouldBe true
        }

        "quat normalize" {

            run {
                val q = Quat().angleAxis(glm.πf * .25f, Vec3(0, 0, 1))
                val n = q.normalize()
                val l = n.length()

                glm.equal(l, 1f, .000001f) shouldBe true
            }
            run {
                val q = Quat().angleAxis(glm.πf * .25f, Vec3(0, 0, 2))
                val n = q.normalize()
                val l = n.length()

                glm.equal(l, 1f, .000001f) shouldBe true
            }
            run {
                val q = Quat().angleAxis(glm.πf * .25f, Vec3(1, 2, 3))
                val n = q.normalize()
                val l = n.length()

                glm.equal(l, 1f, .000001f) shouldBe true
            }
        }

        "quat euler" {

            run {
                val q = Quat(1f, 0f, 0f, 1f)
                val roll = glm.roll(q)
                val pitch = glm.pitch(q)
                val yaw = glm.yaw(q)
                val angles = q.eulerAngles()
            }
            run {
                val q = Quat(1f, 0f, 0f, 1f)
                val roll = glm.roll(q)
                val pitch = glm.pitch(q)
                val yaw = glm.yaw(q)
                val angles = q.eulerAngles()
            }
        }

        "quat slerp" {

            val sqrt2 = glm.sqrt(2f) / 2f
            val id = Quat()
            val y90rot = Quat(sqrt2, 0f, sqrt2, 0f)

            // Testing a == 0
            // Must be id
            val id2 = id.slerp(y90rot, .0f)
            glm.all(glm.equal(id, id2, epsilon)) shouldBe true

            // Testing a == 1
            // Must be 90° rotation on Y : 0 0.7 0 0.7
            val y90rot2 = id.slerp(y90rot, 1f)
            glm.all(glm.equal(y90rot, y90rot2, epsilon)) shouldBe true

            // Testing standard, easy case
            // Must be 45° rotation on Y : 0 0.38 0 0.92
            val y45rot1 = id.slerp(y90rot, .5f)
            glm.all(glm.equal(y45rot1, Quat(.92f, 0f, .38f, 0f), .01f)) shouldBe true

            // Testing reverse case
            // Must be 45° rotation on Y : 0 0.38 0 0.92
            val ym45rot2 = id.slerp(y90rot, .5f)
            glm.all(glm.equal(ym45rot2, Quat(.92f, 0f, .38f, 0f), .01f)) shouldBe true

            // Testing against full circle around the sphere instead of shortest path
            // Must be 45° rotation on Y
            // certainly not a 135° rotation
            val y45rot3 = id.slerp(-y90rot, .5f)
            val y45angle3 = y45rot3.angle()
            glm.equal(y45angle3, glm.πf * .25f, epsilon) shouldBe true
            glm.all(glm.equal(ym45rot2, y45rot3, epsilon)) shouldBe true

            // Same, but inverted
            // Must also be 45° rotation on Y :  0 0.38 0 0.92
            // -0 -0.38 -0 -0.92 is ok too
            val y45rot4 = glm.slerp(-y90rot, id, 0.5f, Quat())
            glm.all(glm.equal(ym45rot2, -y45rot4, epsilon)) shouldBe true

            // Testing q1 = q2
            // Must be 90° rotation on Y : 0 0.7 0 0.7
            val y90rot3 = y90rot.slerp(y90rot, .5f)
            glm.all(glm.equal(y90rot, y90rot3, epsilon)) shouldBe true

            // Testing 180° rotation
            // Must be 90° rotation on almost any axis that is on the XZ plane
            val xz90rot = id.slerp(-y90rot, .5f)
            val xz90angle = xz90rot.angle() // Must be PI/4 = 0.78
            glm.equal(xz90angle, glm.πf * .25f, epsilon) shouldBe true

            // Testing almost equal quaternions (this test should pass through the linear interpolation)
            // Must be 0 0.00X 0 0.99999
            val almostid = id.slerp(glm.angleAxis(.1f, Vec3(0f, 1f, 0f)), .5f, Quat())
            glm.all(glm.equal(almostid, Quat(.99968f, 0.f, .02499f, 0f), epsilon)) shouldBe true

            // Testing quaternions with opposite sign
            run {

                val a = Quat(-1, 0, 0, 0)

                val result = a.slerp(id, .5f)

                val b = id dot result
                val c = glm.pow(id dot result, 2f)
                glm.equal(glm.pow(id dot result, 2f), 1f, .01f) shouldBe true
            }
        }

        "quat times" {

            val temp1 = Quat(1.0f, Vec3(0.0, 1.0, 0.0)).normalize()
            val temp2 = Quat(0.5f, Vec3(1.0, 0.0, 0.0)).normalize()
            val transformed0 = (temp1 * Vec3(0.0, 1.0, 0.0) * temp1.inverse())
            val temp4 = temp2 * transformed0 * temp2.inverse()
            val temp5 = (temp1 * temp2).normalize()
            val temp6 = temp5 * Vec3(0.0, 1.0, 0.0) * temp5.inverse()
            val temp7 = Quat(1f, Vec3(0.0, 1.0, 0.0))
            temp7 timesAssign temp5
            temp7 timesAssign temp5.inverse()

            temp7.anyNotEqual(Quat(1f, Vec3(0.0, 1.0, 0.0))) shouldBe false
        }

        /**
         *  gtx_quaternion
         */

        "quat fastMix" {

            val A = glm.angleAxis(0f, Vec3(0, 0, 1))
            val B = glm.angleAxis(glm.πf * 0.5f, Vec3(0, 0, 1))
            val C = glm.fastMix(A, B, 0.5f)
            val D = glm.angleAxis(glm.πf * 0.25f, Vec3(0, 0, 1))

            glm.equal(C.x, D.x, 0.01f) shouldBe true
            glm.equal(C.y, D.y, 0.01f) shouldBe true
            glm.equal(C.z, D.z, 0.01f) shouldBe true
            glm.equal(C.w, D.w, 0.01f) shouldBe true
        }

        "quat shortMix" {

            val A = glm.angleAxis(0f, Vec3(0, 0, 1))
            val B = glm.angleAxis(glm.πf * 0.5f, Vec3(0, 0, 1))
            val C = glm.shortMix(A, B, 0.5f)
            val D = glm.angleAxis(glm.πf * 0.25f, Vec3(0, 0, 1))

            glm.equal(C.x, D.x, 0.01f) shouldBe true
            glm.equal(C.y, D.y, 0.01f) shouldBe true
            glm.equal(C.z, D.z, 0.01f) shouldBe true
            glm.equal(C.w, D.w, 0.01f) shouldBe true
        }

        "orientation" {

            run {
                val q = Quat(1f, 0f, 0f, 1f)
                val p = glm.roll(q)
                glm.equal(p, glm.πf * 0.5f, 0.0001f) shouldBe true
            }

            run {
                val q = Quat(1f, 0f, 0f, 1f)
                val p = glm.pitch(q)
                glm.equal(p, 0f, 0.0001f) shouldBe true
            }

            run {
                val q = Quat(1f, 0f, 0f, 1f)
                val p = glm.yaw(q)
                glm.equal(p, 0f, 0.0001f) shouldBe true
            }
        }

        "rotation" {

            val v = Vec3(1, 0, 0)
            val u = Vec3(0, 1, 0)

            val rotation = glm.rotation(v, u)

            val angle = glm.angle(rotation)

            (glm.abs(angle - glm.πf * 0.5f) < glm.εf) shouldBe true
        }

        "log" {

            val q = Quat()
            val p = glm.log(q)
            val r = glm.exp(p)
        }

        "quat lookAt" {

            val eye = Vec3(0f)
            val center = Vec3(1.1f, -2f, 3.1416f)
            val up = Vec3(-0.17f, 7.23f, -1.744f)

            // Test left-handed implementation
            val testQuatLH = glm.quatLookAtLH(glm.normalize(center - eye), up)
            val testMatLH = glm.conjugate(glm.quat_cast(glm.lookAtLh(Mat4(), eye, center, up)))
            (glm.abs(glm.length(testQuatLH) - 1f) > glm.εf) shouldBe false
            (glm.min(glm.length(testQuatLH - testMatLH), glm.length(testQuatLH + testMatLH)) > glm.εf) shouldBe false

            // Test right-handed implementation
            val testQuatRH = glm.quatLookAtRH(glm.normalize(center - eye), up)
            val testMatRH = glm.conjugate(glm.quat_cast(glm.lookAtRh(Mat4(), eye, center, up)))
            (glm.abs(glm.length(testQuatRH) - 1f) > glm.εf) shouldBe false
            (glm.min(glm.length(testQuatRH - testMatRH), glm.length(testQuatRH + testMatRH)) > glm.εf) shouldBe false
        }

        "identity"        {

            val Q = Quat.identity

            Q.allEqual(Quat(1, 0, 0, 0), 0.0001f) shouldBe true
            Q.anyNotEqual(Quat(1, 0, 0, 0), 0.0001f) shouldBe false

            val M = Mat4.identity
            val N = Mat4(1f)

            M.allEqual(N, 0.001f) shouldBe true
        }
    }
}