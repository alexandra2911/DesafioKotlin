package com.example.desafiokotlin

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class SmartDevice(val name: String, val category: String) {

    var deviceStatus = "offline"
        protected set

    open val deviceType = "unknown"

    fun printDeviceInfo() {
        println("Device name: $name, category: $category, type: $deviceType")
    }

    open fun turnOn() {
        deviceStatus = "on"
        println("$name turned on")
    }

    open fun turnOff() {
        deviceStatus = "off"
        println("$name turned off")
    }
}

class SmartTvDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart TV"

    private var speakerVolume by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)

    private var channelNumber by RangeRegulator(initialValue = 1, minValue = 0, maxValue = 200)

    fun increaseSpeakerVolume() {
        if (deviceStatus == "on") {
            speakerVolume++
            println("Speaker volume increased to $speakerVolume.")
        }
    }

    fun decreaseSpeakerVolume() {
        if (deviceStatus == "on" && speakerVolume > 0) {
            speakerVolume--
            println("Speaker volume decreased to $speakerVolume.")
        }
    }

    fun nextChannel() {
        if (deviceStatus == "on") {
            channelNumber++
            println("Channel number increased to $channelNumber.")
        }
    }

    fun previousChannel() {
        if (deviceStatus == "on" && channelNumber > 0) {
            channelNumber--
            println("Channel number decreased to $channelNumber.")
        }
    }
}

class SmartLightDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart Light"

    private var brightnessLevel by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)

    fun increaseBrightness() {
        if (deviceStatus == "on") {
            brightnessLevel++
            println("Brightness increased to $brightnessLevel.")
        }
    }

    fun decreaseBrightness() {
        if (deviceStatus == "on" && brightnessLevel > 0) {
            brightnessLevel--
            println("Brightness decreased to $brightnessLevel.")
        }
    }
}

class SmartHome(
    private val smartTvDevice: SmartTvDevice,
    private val smartLightDevice: SmartLightDevice
) {

    private var deviceTurnOnCount = 0

    fun turnOnTv() {
        deviceTurnOnCount++
        smartTvDevice.turnOn()
    }

    private fun turnOffTv() {
        if (smartTvDevice.deviceStatus == "on") {
            deviceTurnOnCount--
            smartTvDevice.turnOff()
        }
    }

    fun increaseTvVolume() {
        smartTvDevice.increaseSpeakerVolume()
    }

    fun decreaseTvVolume() {
        smartTvDevice.decreaseSpeakerVolume()
    }

    fun changeTvChannelToNext() {
        smartTvDevice.nextChannel()
    }

    fun changeTvChannelToPrevious() {
        smartTvDevice.previousChannel()
    }

    fun printSmartTvInfo() {
        smartTvDevice.printDeviceInfo()
    }

    fun turnOnLight() {
        deviceTurnOnCount++
        smartLightDevice.turnOn()
    }

    private fun turnOffLight() {
        if (smartLightDevice.deviceStatus == "on") {
            deviceTurnOnCount--
            smartLightDevice.turnOff()
        }
    }

    fun increaseLightBrightness() {
        smartLightDevice.increaseBrightness()
    }

    fun decreaseLightBrightness() {
        smartLightDevice.decreaseBrightness()
    }

    fun printSmartLightInfo() {
        smartLightDevice.printDeviceInfo()
    }

    fun turnOffAllDevices() {
        turnOffTv()
        turnOffLight()
    }
}

class RangeRegulator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int
) : ReadWriteProperty<Any?, Int> {

    private var fieldData = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value in minValue..maxValue) {
            fieldData = value
        }
    }
}

fun main() {
    val smartTv = SmartTvDevice("Android TV", "Entertainment")
    val smartLight = SmartLightDevice("Google Light", "Utility")
    val smartHome = SmartHome(smartTv, smartLight)

    // Testing SmartTvDevice functionalities
    smartHome.turnOnTv()
    smartHome.increaseTvVolume()
    smartHome.decreaseTvVolume()
    smartHome.changeTvChannelToNext()
    smartHome.changeTvChannelToPrevious()
    smartHome.printSmartTvInfo()

    // Testing SmartLightDevice functionalities
    smartHome.turnOnLight()
    smartHome.increaseLightBrightness()
    smartHome.decreaseLightBrightness()
    smartHome.printSmartLightInfo()

    // Turn off all devices
    smartHome.turnOffAllDevices()
}
