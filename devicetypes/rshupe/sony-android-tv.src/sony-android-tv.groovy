/**
 *  Sony Android TV SmartThings Integration, Currently testing on: XBR-65X750D
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Based on code from Ed Anuff, Jamie Yates, Steve Abratt and Eric Yew
 *
 *  Based on Eric Yew's example:
 *    https://github.com/ericyew/SmartThingsPublic/tree/master/devicetypes/ericyew/sony-android-tv.src
 *
 *  Which in turn was based on Steve Abratt's example:
 *    https://gist.github.com/steveAbratt/43133bf9011febf6437a662eb5998ec8
 *
 *  Which in turn was based on Jamie Yates's example:
 *  	https://gist.github.com/jamieyates79/fd49d23c1dac1add951ec8ba5f0ff8ae
 *
 *  Inspiration from Patrick Mjoen's example:
 *    https://github.com/pmjoen/SmartThingsMjoen/tree/master/devicetypes/pmjoen/sony-xbr-android-tv.src
 *
 *  Note: Within the Device on the SmartThings IDE the Device Network ID for Device instance must be hex of IP address and port
 *  in the form of 00000000:0000 (i.e. 10.0.1.220:80 is 0A0001DC:0050) - if you check the logs the smart device will log the
 *  correct address that you can copy paste, once the IP address has been set. If this is not set you wont get updated on/off status
 *
 *  Please make sure the TV is on when you set up the device.
 *
 *  Wake on Lan button works when the TV is in ECO mode and goes to sleep even on wifi. however it takes a slight bit longer to boot
 *  wake on lan wont yet update the status to on very quickly and status polls happen about every 5 mins so it maybe 5 mins before
 *  the TV shows as on.
 *
 */

// Meta Data
metadata {
    definition (name: "Sony XBR Android TV", namespace: "RodneyShupe", author: "Rodney Shupe") {
        capability "Switch"
        capability "Polling"
        capability "Refresh"
        capability "Media Controller"
        capability "Music Player"
        capability "Switch Level"
        capability "TV"

        command "sendRemoteCommand"
        command "cmdDigital"
        command "cmdPicOff"
        command "tv_source"
        command "hdmi1"
        command "hdmi2"
        command "hdmi3"
        command "hdmi4"
        command "mute"
        command "netflix"
        command "WOLC"
        command "ipaddress"
        command "iphex"
        command "macaddress"
        command "cmdHome"
        command "cmdGGuide"
        command "cmdEPG"
        command "cmdFavorites"
        command "cmdDisplay"
        command "cmdActionMenu"
        command "cmdReturn"
        command "cmdUp"
        command "cmdDown"
        command "cmdRight"
        command "cmdLeft"
        command "cmdConfirm"
        command "cmdRed"
        command "cmdGreen"
        command "cmdYellow"
        command "cmdBlue"
        command "cmdNum1"
        command "cmdNum2"
        command "cmdNum3"
        command "cmdNum4"
        command "cmdNum5"
        command "cmdNum6"
        command "cmdNum7"
        command "cmdNum8"
        command "cmdNum9"
        command "cmdNum0"
        command "cmdNum11"
        command "cmdNum12"
        command "cmdVolumeUp"
        command "cmdVolumeDown"
        command "cmdChannelUp"
        command "cmdChannelDown"
        command "cmdSubTitle"
        command "cmdClosedCaption"
        command "cmdEnter"
        command "cmdDOT"
        command "cmdAnalog"
        command "cmdTeletext"
        command "cmdExit"
        command "cmdAnalog2"
        command "cmdAD"
        command "cmdAnalogg"
        command "cmdBS"
        command "cmdCS"
        command "cmdBSCS"
        command "cmdDdata"
        command "cmdTvRadio"
        command "cmdTheater"
        command "cmdSEN"
        command "cmdInternetWidgets"
        command "cmdInternetVideo"
        command "cmdSceneSelect"
        command "cmdMode3D"
        command "cmdIManual"
        command "cmdAudio"
        command "cmdWide"
        command "cmdJump"
        command "cmdPAP"
        command "cmdMyEPG"
        command "cmdProgramDescription"
        command "cmdWriteChapter"
        command "cmdTrackID"
        command "cmdTenKey"
        command "cmdAppliCast"
        command "cmdAcTVila"
        command "cmdDeleteVideo"
        command "cmdPhotoFrame"
        command "cmdTvPause"
        command "cmdKeyPad"
        command "cmdMedia"
        command "cmdForward"
        command "cmdPlay"
        command "cmdRewind"
        command "cmdPrev"
        command "cmdStop"
        command "cmdNext"
        command "cmdRec"
        command "cmdPause"
        command "cmdEject"
        command "cmdFlashPlus"
        command "cmdFlashMinus"
        command "cmdTopMenu"
        command "cmdRakurakuStart"
        command "cmdOneTouchTimeRec"
        command "cmdOneTouchView"
        command "cmdOneTouchRec"
        command "OneTouchStop"
        command "cmdDUX"
        command "cmdFootballMode"
        command "ScmdyncMenu"
        command "cmdWirelessSubwoofer"
    }

    simulator {
        status "on": "on/off: 1"
        status "off": "on/off: 0"
    }

    tiles(scale: 2) {
        multiAttributeTile(name: "TVMulti", type:"generic", width:6, height:2) {
            tileAttribute("device.switch", key: "PRIMARY_CONTROL") {
                attributeState "on", label:'ON', backgroundColor:"#00A0DC", nextState:"turningOff"
                attributeState "off", label:'${name}', backgroundColor:"#ffffff", nextState:"turningOn"
                attributeState "turningOn", label:'${name}', backgroundColor:"#79b821", nextState:"turningOff"
                attributeState "turningOff", label:'${name}', backgroundColor:"#ffffff", nextState:"turningOn"
            }
            tileAttribute("device.switch", key: "SECONDARY_CONTROL") {
                attributeState "on", label:'${name}', action:"switch.off", icon:"st.switches.switch.on", backgroundColor:"#00A0DC", nextState:"turningOff"
                attributeState "off", label:'${name}', action:"switch.on", backgroundColor:"#ffffff", nextState:"turningOn"
                attributeState "turningOn", label:'…', action:"switch.off", icon:"st.switches.switch.on", backgroundColor:"#79b821", nextState:"turningOff"
                attributeState "turningOff", label:'…', action:"switch.on", backgroundColor:"#ffffff", nextState:"turningOn"
            }
            tileAttribute("device.level", key: "VALUE_CONTROL") {
                attributeState "VALUE_UP", action: "cmdVolumeUp"
                attributeState "VALUE_DOWN", action: "cmdVolumeDown"
            }
        }

        standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
            state "off", label: '${name}', action: "switch.on", icon: "st.thermostat.heating-cooling-off", backgroundColor: "#ffffff"
            state "on", label: 'ON', action: "switch.off", icon: "st.thermostat.heating-cooling-on", backgroundColor: "#79b821"
        }

        standardTile("power", "device.switch", width: 1, height: 1, canChangeIcon: false) {
            state "off", label: '', action: "on", icon:"st.thermostat.heating-cooling-off", backgroundColor: "#ffffff"//, nextState: "on"
            state "on", label: '', action: "off", icon:"st.thermostat.heating-cooling-off", backgroundColor: "#79b821"//, nextState: "off"
        }

        standardTile("tileOn", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"On", action:"cmdOn", icon:""
        }

        standardTile("tileOff", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Off", action:"cmdOff", icon:""
        }

        standardTile("tileDigital", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Digital", action:"cmdDigital", icon:""
        }

        standardTile("tilePicOff", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Pic Off", action:"cmdPicOff", icon:""
        }

        standardTile("tileRefresh", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
        }

        standardTile("tileTvSource", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Source", action:"cmdTvSource", icon:""
        }

        standardTile("tileWOLC", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Wake on Lan", action:"cmdWOLC", icon:""
        }

        standardTile("tileHdmi1", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"HDMI 1", action:"cmdHdmi1", icon:""
        }

        standardTile("tileHdmi2", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"HDMI 2", action:"cmdHdmi2", icon:""
        }

        standardTile("tileHdmi3", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"HDMI 3", action:"cmdHdmi3", icon:""
        }

        standardTile("tileHdmi4", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"HDMI 4", action:"cmdHdmi4", icon:""
        }

        standardTile("tileNetflix", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Netflix", action:"cmdNetflix", icon:""
        }

        standardTile("tileHome", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Home", action:"cmdHome", icon:"${getUserTheme('default','iconHome')}"
        }

        standardTile("tileMute", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Mute", action:"cmdMute", icon:""
        }

        standardTile("tileGGuide", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"GGuide", action:"cmdGGuide", icon:""
        }

        standardTile("tileEPG", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Guide", action:"cmdEPG", icon:""
        }

        standardTile("tileFavorites", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Favorites", action:"cmdFavorites", icon:""
        }

        standardTile("tileDisplay", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"", action:"cmdDisplay", icon:"${getUserTheme('default', 'iconInfo')}"
        }

        standardTile("tileActionMenu", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"", action:"cmdActionMenu", icon:"${getUserTheme('default', 'iconMenu')}"
        }

        standardTile("tileConfirm", "device.switch", inactiveLabel: false, height: 2, width: 2, decoration: "${getUserPref('decPush')}") {
            state "default", label:"Select", action:"cmdConfirm", backgroundColor:"${getUserPref('colSelectActive')}"
        }

        standardTile("tileUp", "device.switch", inactiveLabel: false, height: 1, width: 2, decoration: "flat") {
            state "default", label:"", action:"cmdUp", icon:"${getUserTheme('default','iconUp')}"
        }

        standardTile("tileReturn", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Return", action:"cmdReturn", icon:""
        }

        standardTile("tileRed", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Red", action:"cmdRed", icon:"", backgroundColor: "#ff0000"
        }

        standardTile("tileGreen", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Green", action:"cmdGreen", icon:"", backgroundColor: "#008000"
        }

        standardTile("tileYellow", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Yellow", action:"cmdYellow", icon:"", backgroundColor: "#ffff00"
        }

        standardTile("tileDown", "device.switch", inactiveLabel: false, height: 1, width: 2, decoration: "flat") {
            state "default", label:"", action:"cmdDown", icon:"${getUserTheme('default','iconDown')}"
        }

        standardTile("tileRight", "device.switch", inactiveLabel: false, height: 2, width: 1, decoration: "flat") {
            state "default", label:"", action:"cmdRight", icon:"${getUserTheme('default','iconRight')}"
        }

        standardTile("tileLeft", "device.switch", inactiveLabel: false, height: 2, width: 1, decoration: "flat") {
            state "default", label:"", action:"cmdLeft", icon:"${getUserTheme('default','iconLeft')}"
        }

        standardTile("tileBlue", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Blue", action:"cmdBlue", icon:"", backgroundColor: "#0000ff"
        }

        standardTile("tileNum1", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num1", action:"cmdNum1", icon:""
        }

        standardTile("tileNum2", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num2", action:"cmdNum2", icon:""
        }

        standardTile("tileNum3", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num3", action:"cmdNum3", icon:""
        }

        standardTile("tileNum4", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num4", action:"cmdNum4", icon:""
        }

        standardTile("tileNum5", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num5", action:"cmdNum5", icon:""
        }

        standardTile("tileNum6", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num6", action:"cmdNum6", icon:""
        }

        standardTile("tileNum7", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num7", action:"cmdNum7", icon:""
        }

        standardTile("tileNum8", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num8", action:"num8", icon:""
        }

        standardTile("tileNum9", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num9", action:"cmdNum9", icon:""
        }

        standardTile("tileNum0", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num0", action:"cmdNum0", icon:""
        }

        standardTile("tileNum11", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num11", action:"cmdNum11", icon:""
        }

        standardTile("tileNum12", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"num12", action:"cmdNum12", icon:""
        }

        standardTile("tileVolumeUp", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Vol", action:"cmdVolumeUp", icon:"${getUserTheme('default','iconUp')}"
        }

        standardTile("tileVolumeDown", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Vol", action:"cmdVolumeDown", icon:"${getUserTheme('default','iconDown')}"
        }

        standardTile("tileChannelUp", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Channel", action:"cmdChannelUp", icon:"${getUserTheme('default','iconUp')}"
        }

        standardTile("tileChannelDown", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Channel", action:"cmdChannelDown", icon:"${getUserTheme('default','iconDown')}"
        }

        standardTile("tileSubTitle", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"SubTitle", action:"cmdSubTitle", icon:""
        }

        standardTile("tileClosedCaption", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"CC", action:"cmdClosedCaption", icon:""
        }

        standardTile("tileEnter", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Enter", action:"cmdEnter", icon:""
        }

        standardTile("tileDOT", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"DOT", action:"cmdDOT", icon:""
        }

        standardTile("tileAnalog", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Analog", action:"cmdAnalog", icon:""
        }

        standardTile("tileTeletext", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Teletext", action:"cmdTeletext", icon:""
        }

        standardTile("tileExit", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Exit", action:"cmdExit", icon:""
        }

        standardTile("tileMode3D", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"3D", action:"cmdMode3D", icon:""
        }

        standardTile("tileIManual", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Help", action:"cmdIManual", icon:""
        }

        standardTile("tileAudio", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Audio", action:"cmdAudio", icon:""
        }

        standardTile("tileWide", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Aspect Ratio", action:"cmdWide", icon:""
        }

        standardTile("tileJump", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Jump", action:"cmdJump", icon:""
        }

        standardTile("tilePAP", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"PIP", action:"cmdPAP", icon:""
        }

        standardTile("tilePhotoFrame", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Recorded", action:"cmdPhotoFrame", icon:""
        }

        standardTile("tileMedia", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Streaming", action:"cmdMedia", icon:""
        }

        standardTile("tileSyncMenu", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Sync Menu", action:"cmdSyncMenu", icon:""
        }

        standardTile("tileForward", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Forward", action:"cmdForward", icon:""
        }

        standardTile("tilePlay", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Play", action:"cmdPlay", icon:""
        }

        standardTile("tileRewind", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Rewind", action:"cmdRewind", icon:""
        }

        standardTile("tilePrev", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Prev", action:"cmdPrev", icon:""
        }

        standardTile("tileStop", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Stop", action:"cmdStop", icon:""
        }

        standardTile("tileNext", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Next", action:"cmdNext", icon:""
        }

        standardTile("tileRec", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Rec", action:"cmdRec", icon:""
        }

        standardTile("tilePause", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Pause", action:"cmdPause", icon:""
        }

        standardTile("tileEject", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Eject", action:"cmdEject", icon:""
        }

        standardTile("tileOneTouchView", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"OneTouchView", action:"cmdOneTouchView", icon:""
        }

        standardTile("tileOneTouchTimeRec", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"OneTouchTimeRec", action:"cmdOneTouchTimeRec", icon:""
        }

        standardTile("tileOneTouchRec", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"OneTouchRec", action:"cmdOneTouchRec", icon:""
        }

        standardTile("tileOneTouchStop", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"OneTouchStop", action:"cmdOneTouchStop", icon:""
        }

        standardTile("tileDUX", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Discovery", action:"DUX", icon:""
        }

        standardTile("FootballMode", "device.switch", inactiveLabel: false, height: 1, width: 1, decoration: "flat") {
            state "default", label:"Football Mode", action:"cmdFootballMode", icon:""
        }

        controlTile("tileVolume", "device.level", "slider", height: 1,width: 2) {
        	state "level", action:"switch level.setLevel"
        }

        main("switch")

        /**uncomment any extra tiles you need from the lines below*/
        details(["tileHdmi1", "tileHdmi2", "tileHdmi3", "tileHdmi4",
            "tileSwitch", "tileTvSource", "tileNetflix", "tileMedia",
            "tileDigital", "tilePicOff", "tileDisplay", "tileUp", "tileEPG",
            "tileDUX", "tileChannelUp", "tileLeft", "tileConfirm", "tileRight",
            "tileVolumeUp", "tileChannelDown", "tileVolumeDown",
            "tileRefresh", "tileActionMenu", "tileDown", "tileHome", "tileMute",
            "tileYellow", "tileBlue", "tileRed", "tileGreen",
            "tileSyncMenu", "tileForward", "tilePlay", "tileRewind", "tilePrev",
            "tileStop", "tileNext", "tileRec", "tilePause", "tileEject",
            "tileReturn", "tilePower", "tileOn", "tileOff",
            "tileNum1", "tileNum2", "tileNum3", "tileNum4", "tileNum5",
            "tileNum6", "tileNum7", "tileNum8", "tileNum9", "tileNum0",
            "tileNum11", "tileNum12", "tileSubTitle", "tileClosedCaption",
            "tileEnter", "tileDOT", "tileAnalog", "tileTeletext", "tileExit",
            "tileMode3D", "tileIManual", "tileAudio", "tileWide", "tileJump",
            "tilePAP", "tileMyEPG", "tileProgramDescription", "tileWriteChapter",
            "tileTrackID", "tileTenKey", "tileAppliCast", "tileAcTVila",
            "tileDeleteVideo", "tilePhotoFrame", "tileTvPause", "tileKeyPad",
            "tileMedia", "tileFlashPlus", "tileFlashMinus", "tileTopMenu",
            "tileRakurakuStart", "tileOneTouchTimeRec", "tileOneTouchView",
            "tileOneTouchRec", "tileOneTouchStop", "tileDUX", "tileGGuide",
            "tileFootballMode", "tileSocial", "tileWOLC"])
    }

    preferences {
    	input name: "ipadd1", type: "number", range: "0..254", required: true, title: "Ip address part 1", displayDuringSetup: true
    	input name: "ipadd2", type: "number", range: "0..254", required: true, title: "Ip address part 2", displayDuringSetup: true
    	input name: "ipadd3", type: "number", range: "0..254", required: true, title: "Ip address part 3", displayDuringSetup: true
    	input name: "ipadd4", type: "number", range: "0..254", required: true, title: "Ip address part 4", displayDuringSetup: true
    	input name: "tv_port", type: "number", range: "0..9999", required: true, title: "Port Usually: 80", displayDuringSetup: true
    	input name: "tv_psk", type: "text", title: "PSK Passphrase Set on your TV", description: "Enter passphrase", required: true, displayDuringSetup: true
    }
}

def getDefaultTheme(){
    def userDefaultThemeMap = [:]
    //v1.2 START
    //ICONS
    userDefaultThemeMap.themeName = "Default"
    userDefaultThemeMap.iconStop = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/stop-icon.png"
    userDefaultThemeMap.iconShutdown = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/power-icon.png"
    userDefaultThemeMap.iconUp = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/up-icon.png"
    userDefaultThemeMap.iconDown = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/down-icon.png"
    userDefaultThemeMap.iconLeft = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/left-icon.png"
    userDefaultThemeMap.iconRight = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/right-icon.png"
    userDefaultThemeMap.iconBack = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/back-icon.png"
    userDefaultThemeMap.iconInfo = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/info-icon.png"
    userDefaultThemeMap.iconSkipFwd = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/small-fwd-icon.png"
    userDefaultThemeMap.iconSkipRwd = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/small-rwd-icon.png"
    userDefaultThemeMap.iconNext = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/next-icon.png"
    userDefaultThemeMap.iconPrevious = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/prev-icon.png"
    userDefaultThemeMap.iconMenu = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/menu-icon.png"
    userDefaultThemeMap.iconHome = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/home-icon.png"
    userDefaultThemeMap.iconPgUp = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/pg-up-icon.png"
    userDefaultThemeMap.iconPgDown = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/themes/default/pg-down-icon.png"
    //COLOURS
    userDefaultThemeMap.colMainWaiting = "#ffffff"     //White
    userDefaultThemeMap.colMainStartup = "#90d2a7"     //Light Green
    userDefaultThemeMap.colMainPlaying = "#79b821"     //Green
    userDefaultThemeMap.colMainStopped = "#153591"     //Blue
    userDefaultThemeMap.colMainPaused = "#e86d13"      //Orange
    userDefaultThemeMap.colMainShutdown = "#e84e4e"    //Red
    //v1.2 END

    //Return
    return userDefaultThemeMap
}

def getUserPref(pref){
    def prefsMap = [:]
    //Main Icon
    prefsMap.iconMain = "https://raw.githubusercontent.com/north3221/north3221SmartThings/master/resources/main-icon.png"
    //Select Colour
    prefsMap.colSelectActive = "#22a3ec"    //Blue
    prefsMap.colSelectInactive = "#ffffff"  //White
    //DECORATION
    prefsMap.decPush = "ring"
    prefsMap.decStop = "ring"
    prefsMap.decShutdown = "flat"
    prefsMap.decUp = "flat"
    prefsMap.decDown = "flat"
    prefsMap.decLeft = "flat"
    prefsMap.decRight = "flat"
    prefsMap.decBack = "flat"
    prefsMap.decInfo = "ring"
    prefsMap.decSkipF = "flat"
    prefsMap.decSkipB = "flat"
    prefsMap.decNext = "flat"
    prefsMap.decPrev = "flat"
    prefsMap.decMenu = "flat"
    prefsMap.decHome = "flat"
    prefsMap.decPup = "flat"
    prefsMap.decPdown = "flat"
    //CATEGORY SETTINGS
    prefsMap.movieLabels = "cinema, movie, film"
    prefsMap.sportLabels = "sport"
    prefsMap.tvLabels = "ctv, cbc, global, cbs, abc, hbo, amc, fox"
    prefsMap.minMovieRuntime = 4200
    return prefsMap[pref]
}

def updated(){
	log.debug( "Preferences Updated rebuilding IP Address, MAC address and Hex Network ID")
	state.tvPollCount = 0
	ipaddress()
	iphex()
	refresh()
}

def ipaddress(){
	//Build an IP Address from the 4 input preferences
	log.debug( "building IP address from Preferences")
	state.tv_ip = "${ipadd1}" + "." + "${ipadd2}" + "." + "${ipadd3}" + "." + "${ipadd4}"
	log.debug( "IP Address State Value Set to = ${state.tv_ip}:${tv_port}" )
}

def iphex(){
	//create a Hex of the IP this will need to be set as the Network ID
	//TO DO Set the Network IP automatically or Show the user the Value to set manually
	log.debug( "Creating Hex of IP: ${state.tv_ip}")

	String tvipstring = state.tv_ip
	String tv_ip_hex = tvipstring.tokenize( '.' ).collect {
		String.format( '%02x', it.toInteger() )
	}.join()

	//set the global value of state.ip_hex
	state.ip_hex = tv_ip_hex
	log.debug ("IP Hex stored Globaly as '${state.ip_hex}'")

	log.debug( "Creating Hex of Port: ${tv_port}")

    String tvportstring = tv_port
    String tv_port_hex = tvportstring.tokenize( '.' ).collect {
    	String.format( '%04x', it.toInteger() )
    }.join()

    //Set the Global Value of state.port_hex
    state.port_hex = tv_port_hex
    log.debug ("Port Hex stored Globaly as '${state.port_hex}'")

    log.debug( "Please set your Device Network ID to the following to allow the TV state to be captured: ${state.ip_hex}:${state.port_hex}" )
    String netid = ("${state.ip_hex}:${state.port_hex}")
    log.debug( "Netid ${netid}" )
    //device.deviceNetworkId = ("${netid}")
}

def parse(description) {
    //log.debug ("Parsing '${description}'")
    def msg = parseLanMessage(description)
    //Set the Global Value of state.tv_mac
    //log.debug "${msg}"
    state.tv_mac = msg.mac
    log.debug ("MAC Address stored Globally as '${state.tv_mac}'")
    //log.debug "msg '${msg}'"
    //log.debug "msg.json '${msg.json?.id}'"


    if (msg.json?.id == 2) {
    	//Set the Global value of state.tv on or off
        state.tv = (msg.json.result[0]?.status == "active") ? "on" : "off"
        sendEvent(name: "switch", value: state.tv)
        log.debug "TV is '${state.tv}'"
        state.tvPollCount = 0
    }
}

private sendJsonRpcCommand(json) {
    def headers = [:]
    headers.put("HOST", "${state.tv_ip}:${tv_port}")
    headers.put("Content-Type", "application/json")
    headers.put("X-Auth-PSK", "${tv_psk}")

    def result = new physicalgraph.device.HubAction(
        method: 'POST',
        path: '/sony/system',
        body: json,
        headers: headers
    )

    result
}

def installed() {
    log.debug "Executing 'installed'"
    poll()
}

def refresh() {
    log.debug "Executing 'refresh'"
    poll()
}

def poll() {
    //set state.tv to 0ff
    log.debug "poll count ${state.tvPollCount}"
    state.tv = "polling"
    state.tvPollCount = (state.tvPollCount + 1)
    if (state.tvPollCount > 1 ) {
        sendEvent(name: "switch", value: "off")
    }
    log.debug "Executing 'poll'"
    def json = "{\"id\":2,\"method\":\"getPowerStatus\",\"version\":\"1.0\",\"params\":[]}"
    def result = sendJsonRpcCommand(json)
}


/**-------------------------------------------------------
All remote Functions Assigned below
--------------------------------------------------------*/
def setLevel(vol) { state.remoteCommand = "*SCVOLU0000000000000" + String.format('%03d',vol) }

def on() {
    log.debug "Executing 'on'"
    if (state.tv == "polling"){
        WOLC()
    } else {
        def json = "{\"method\":\"setPowerStatus\",\"version\":\"1.0\",\"params\":[{\"status\":true}],\"id\":102}"
        def result = sendJsonRpcCommand(json)
    }
}

def off() {
    log.debug "Executing 'off'"
    def json = "{\"method\":\"setPowerStatus\",\"version\":\"1.0\",\"params\":[{\"status\":false}],\"id\":102}"
    def result = sendJsonRpcCommand(json)
}

def cmdDigital() {         sendRemoteCommand("AAAAAgAAAJcAAAAyAw==", "Digital") }
def cmdPicOff() {          sendRemoteCommand("AAAAAQAAAAEAAAA+Aw==", "Pic Off") }
def cmdGGuide() {          sendRemoteCommand("AAAAAQAAAAEAAAAOAw==", "GGuide") }
def cmdEPG() {             sendRemoteCommand("AAAAAgAAAKQAAABbAw==", "EPG") }
def cmdFavorites() {       sendRemoteCommand("AAAAAgAAAHcAAAB2Aw==", "Favorites") }
def cmdDisplay() {         sendRemoteCommand("AAAAAQAAAAEAAAA6Aw==", "Display") }
def cmdHome() {            sendRemoteCommand("AAAAAQAAAAEAAABgAw==", "Home") }
def cmdActionMenu() {      sendRemoteCommand("AAAAAgAAAMQAAABLAw==", "Action Menu") } // Was the same code as Options
def cmdReturn() {          sendRemoteCommand("AAAAAgAAAJcAAAAjAw==", "Return") }
def cmdUp() {              sendRemoteCommand("AAAAAQAAAAEAAAB0Aw==", "Up") }
def cmdDown() {            sendRemoteCommand("AAAAAQAAAAEAAAB1Aw==", "Down") }
def cmdRight() {           sendRemoteCommand("AAAAAQAAAAEAAAAzAw==", "Right") }
def cmdLeft() {            sendRemoteCommand("AAAAAQAAAAEAAAA0Aw==", "Left") }
def cmdConfirm() {         sendRemoteCommand("AAAAAQAAAAEAAABlAw==", "Confirm") }
def cmdBlue() {            sendRemoteCommand("AAAAAgAAAJcAAAAkAw==", "Blue") }
def cmdRed() {             sendRemoteCommand("AAAAAgAAAJcAAAAlAw==", "Red") }
def cmdGreen() {           sendRemoteCommand("AAAAAgAAAJcAAAAmAw==", "Green") }
def cmdYellow() {          sendRemoteCommand("AAAAAgAAAJcAAAAnAw==", "Yellow") }
def cmdNum1() {            sendRemoteCommand("AAAAAQAAAAEAAAAAAw==", "Num1") }
def cmdNum2() {            sendRemoteCommand("AAAAAQAAAAEAAAABAw==", "Num2") }
def cmdNum3() {            sendRemoteCommand("AAAAAQAAAAEAAAACAw==", "Num3") }
def cmdNum4() {            sendRemoteCommand("AAAAAQAAAAEAAAADAw==", "Num4") }
def cmdNum5() {            sendRemoteCommand("AAAAAQAAAAEAAAAEAw==", "Num5") }
def cmdNum6() {            sendRemoteCommand("AAAAAQAAAAEAAAAFAw==", "Num6") }
def cmdNum7() {            sendRemoteCommand("AAAAAQAAAAEAAAAGAw==", "Num7") }
def cmdNum8() {            sendRemoteCommand("AAAAAQAAAAEAAAAHAw==", "Num8") }
def cmdNum9() {            sendRemoteCommand("AAAAAQAAAAEAAAAIAw==", "Num9") }
def cmdNum0() {            sendRemoteCommand("AAAAAQAAAAEAAAAJAw==", "Num0") }
def cmdNum11() {           cmdTV() }
def cmdNum12() {           tv_source() }
def cmdVolumeUp() {        sendRemoteCommand("AAAAAQAAAAEAAAASAw==", "Volume Up") }
def cmdVolumeDown() {      sendRemoteCommand("AAAAAQAAAAEAAAATAw==", "Volume Down") }
def cmdChannelUp() {       sendRemoteCommand("AAAAAQAAAAEAAAAQAw==", "Channel Up") }
def cmdChannelDown() {     sendRemoteCommand("AAAAAQAAAAEAAAARAw==", "Channel Down") }
def cmdSubTitle() {        sendRemoteCommand("AAAAAgAAAJcAAAAoAw==", "SubTitle") }
def cmdClosedCaption() {   sendRemoteCommand("AAAAAgAAAKQAAAAQAw==", "ClosedCaption") }
def cmdEnter() {           cmdTvSource() }
def cmdDOT() {             sendRemoteCommand("AAAAAgAAAJcAAAAdAw==", "DOT") }
def cmdAnalog() {          sendRemoteCommand("AAAAAgAAAHcAAAANAw==", "Analog") }
def cmdTeletext() {        sendRemoteCommand("AAAAAQAAAAEAAAA/Aw==", "Teletext") }
def cmdExit() {            sendRemoteCommand("AAAAAQAAAAEAAABjAw==", "Exit") }
def cmdAnalog2() {         sendRemoteCommand("AAAAAQAAAAEAAAA4Aw==", "Analog2") }
def cmdAD() {              sendRemoteCommand("AAAAAgAAABoAAAA7Aw==", "*AD") }
def cmdAnalogg() {         sendRemoteCommand("AAAAAgAAAJcAAAAuAw==", "Analog?") }
def cmdBS() {              sendRemoteCommand("AAAAAgAAAJcAAAAsAw==", "BS") }
def cmdCS() {              sendRemoteCommand("AAAAAgAAAJcAAAArAw==", "CS") }
def cmdBSCS() {            sendRemoteCommand("AAAAAgAAAJcAAAAQAw==", "BSCS") }
def cmdDdata() {           sendRemoteCommand("AAAAAgAAAJcAAAAVAw==", "Ddata") }
def cmdTvRadio() {         sendRemoteCommand("AAAAAgAAABoAAABXAw==", "TV Radio") }
def cmdTheater() {         sendRemoteCommand("AAAAAgAAAHcAAABgAw==", "Theater") }
def cmdSEN() {             sendRemoteCommand("AAAAAgAAABoAAAB9Aw==", "SEN") }
def cmdInternetWidgets() { sendRemoteCommand("AAAAAgAAABoAAAB6Aw==", "InternetWidgets") }
def cmdInternetVideo() {   sendRemoteCommand("AAAAAgAAABoAAAB5Aw==", "InternetVideo") }
def cmdNetflix() {         sendRemoteCommand("AAAAAgAAABoAAAB8Aw==", "Netflix") }
def cmdSceneSelect() {     sendRemoteCommand("AAAAAgAAABoAAAB4Aw==", "SceneSelect") }
def cmdMode3D() {          sendRemoteCommand("AAAAAgAAAHcAAABNAw==", "Mode3D") }
def cmdIManual() {         sendRemoteCommand("AAAAAgAAABoAAAB7Aw==", "iManual") }
def cmdAudio() {           sendRemoteCommand("AAAAAQAAAAEAAAAXAw==", "Audio") }
def cmdWide() {            sendRemoteCommand("AAAAAgAAAKQAAAA9Aw==", "Wide") }
def cmdJump() {            sendRemoteCommand("AAAAAQAAAAEAAAA7Aw==", "Jump") }
def cmdPAP() {             sendRemoteCommand("AAAAAgAAAKQAAAB3Aw==", "PAP") }
def cmdMyEPG() {           sendRemoteCommand("AAAAAgAAAHcAAABrAw==", "MyEPG") }
def cmdProgramDescription() { sendRemoteCommand("AAAAAgAAAJcAAAAWAw==", "ProgramDescription") }
def cmdWriteChapter() {    sendRemoteCommand("AAAAAgAAAHcAAABsAw==", "WriteChapter") } //DigitalToggle
def cmdTrackID() {         sendRemoteCommand("AAAAAgAAABoAAAB+Aw==", "TrackID") }
def cmdTenKey() {          cmdGreen() }
def cmdAppliCast() {       cmdSyncMenu() }
def cmdDeleteVideo() {     sendRemoteCommand("AAAAAgAAAHcAAAAfAw==", "DeleteVideo") }
def cmdPhotoFrame() {      sendRemoteCommand("AAAAAgAAABoAAABVAw==", "PhotoFrame") }
def cmdTvPause() {         sendRemoteCommand("AAAAAgAAABoAAABnAw==", "TV Pause") }
def cmdKeyPad() {          sendRemoteCommand("AAAAAgAAABoAAAB1Aw==", "KeyPad") }
def cmdMedia() {           sendRemoteCommand("AAAAAgAAAJcAAAA4Aw==", "Media") }
def cmdSyncMenu() {        sendRemoteCommand("AAAAAgAAABoAAABYAw==", "SyncMenu") }
def cmdPlay() {            sendRemoteCommand("AAAAAgAAAJcAAAAaAw==", "Play") }
def cmdRewind() {          sendRemoteCommand("AAAAAgAAAJcAAAAbAw==", "Rewind") }
def cmdForward() {         sendRemoteCommand("AAAAAgAAAJcAAAAcAw==", "Forward") }
def cmdStop() {            cmdDigital() }
def cmdPrev() {            sendRemoteCommand("AAAAAgAAAJcAAAA8Aw==", "Prev") }
def cmdNext() {            sendRemoteCommand("AAAAAgAAAJcAAAA9Aw==", "Next") }
def cmdRec() {             sendRemoteCommand("AAAAAgAAAJcAAAAgAw==", "Rec") }
def cmdPause() {           sendRemoteCommand("AAAAAgAAAJcAAAAZAw==", "Pause") }
def cmdEject() {           sendRemoteCommand("AAAAAgAAAJcAAABIAw==", "Eject") }
def cmdFlashPlus() {       sendRemoteCommand("AAAAAgAAAJcAAAB4Aw==", "FlashPlus") }
def cmdFlashMinus() {      sendRemoteCommand("AAAAAgAAAJcAAAB5Aw==", "FlashMinus") }
def cmdTopMenu() {         sendRemoteCommand("AAAAAgAAABoAAABgAw==", "TopMenu") }
def cmdPopUpMenu() {       sendRemoteCommand("AAAAAgAAABoAAABhAw==", "Pop Up Menu") }
def cmdRakurakuStart() {   sendRemoteCommand("AAAAAgAAAHcAAABqAw==", "RakurakuStart") }
def cmdOneTouchView() {    sendRemoteCommand("AAAAAgAAABoAAABlAw==", "OneTouchView") }
def cmdOneTouchTimeRec() { sendRemoteCommand("AAAAAgAAABoAAABkAw==", "OneTouchTimeRec") }
def cmdOneTouchRec() {     sendRemoteCommand("AAAAAgAAABoAAABiAw==", "OneTouchRec") }
def cmdOneTouchStop() {    sendRemoteCommand("AAAAAgAAABoAAABjAw==", "OneTouchStop") }
def cmdDUX() {             sendRemoteCommand("AAAAAgAAABoAAABzAw==", "DUX") }
def cmdFootballMode() {    sendRemoteCommand("AAAAAgAAABoAAAB2Aw==", "FootballMode") }
def cmdSocial() {          sendRemoteCommand("AAAAAgAAABoAAAB0Aw==", "Social") }
def cmdTvSource() {        sendRemoteCommand("AAAAAQAAAAEAAAAlAw==", "Input") }
def cmdHdmi1() {           sendRemoteCommand("AAAAAgAAABoAAABaAw==", "HDMI1") }
def cmdHdmi2() {           sendRemoteCommand("AAAAAgAAABoAAABbAw==", "HDMI2") }
def cmdHdmi3() {           sendRemoteCommand("AAAAAgAAABoAAABcAw==", "HDMI3") }
def cmdHdmi4() {           sendRemoteCommand("AAAAAgAAABoAAABdAw==", "HDMI4") }
def cmdMute() {            sendRemoteCommand("AAAAAQAAAAEAAAAUAw==", "Mute") }

def cmdTV() {              sendRemoteCommand("AAAAAQAAAAEAAAAkAw==", "TV") }
def cmdPowerOn() {         cmdMute() }
def cmdPowerOff() {        sendRemoteCommand("AAAAAQAAAAEAAAAVAw==", "Power Off") }
def cmdSleepTimer() {      sendRemoteCommand("AAAAAQAAAAEAAAA2Aw==", "SleepTimer") }
def cmdVideo1() {          sendRemoteCommand("AAAAAQAAAAEAAABAAw==", "Video1") }
def cmdVideo2() {          sendRemoteCommand("AAAAAQAAAAEAAABBAw==", "Video2") }
def cmdAnalogRgb1() {      sendRemoteCommand("AAAAAQAAAAEAAABDAw==", "") }
def cmdPictureMode() {     sendRemoteCommand("AAAAAQAAAAEAAABkAw==", "Picture Mode") }
def cmdComponent1() {      sendRemoteCommand("AAAAAgAAAKQAAAA2Aw==", "Component1") }
def cmdComponent2() {      sendRemoteCommand("AAAAAgAAAKQAAAA3Aw==", "Component2") }
def cmdOptions() {         sendRemoteCommand("AAAAAgAAAJcAAAA2Aw==", "Options") }
def cmdDPadCenter() {      sendRemoteCommand("AAAAAgAAAJcAAABKAw==", "DPad Center") }
def cmdCursorLeft() {      sendRemoteCommand("AAAAAgAAAJcAAABNAw==", "Cursor Left") }
def cmdCursorRight() {     sendRemoteCommand("AAAAAgAAAJcAAABOAw=='", "Cursor Right") }
def cmdCursorUp() {        sendRemoteCommand("AAAAAgAAAJcAAABPAw==", "Cursor Up") }
def cmdCursorDown() {      sendRemoteCommand("AAAAAgAAAJcAAABQAw==", "Cursor Down") }
def cmdShopRemoteControlForcedDynamic() { sendRemoteCommand("AAAAAgAAAJcAAABqAw==", "ShopRemoteControlForcedDynamic") }
def cmdAudioQualityMode(){ sendRemoteCommand("AAAAAgAAAJcAAAB7Aw==", "AudioQualityMode") }
def cmdDemoMode() {        sendRemoteCommand("AAAAAgAAAJcAAAB8Aw==", "DemoMode") }
def cmdDemoSurround() {    sendRemoteCommand("AAAAAgAAAHcAAAB7Aw==", "DemoSurround") }
def cmdAudioMixUp() {      sendRemoteCommand("AAAAAgAAABoAAAA8Aw==", "Audio Mix Up") }
def cmdAudioMixDown() {    sendRemoteCommand("AAAAAgAAABoAAAA9Aw=="), "Audio Mix Down" }
def cmdAssists() {         sendRemoteCommand("AAAAAgAAAMQAAAA7Aw==", "Assists") }
def cmdHelp() {            sendRemoteCommand("AAAAAgAAAMQAAABNAw==", "Help") }
def cmdTvSatellite() {     sendRemoteCommand("AAAAAgAAAMQAAABOAw==", "TvSatellite") }
def cmdWirelessSubwoofer(){sendRemoteCommand("AAAAAgAAAMQAAAB+Aw==", "WirelessSubwoofer")}

def sendRemoteCommand(code, button){
    log.debug "Sending Button: ${button} ${code}"

    state.remoteCommand = code
    state.button = button

    def rawcmd = "${state.remoteCommand}"
    def sonycmd = new physicalgraph.device.HubSoapAction(
        path:    '/sony/IRCC',
        urn:     "urn:schemas-sony-com:service:IRCC:1",
        action:  "X_SendIRCC",
        body:    ["IRCCCode":rawcmd],
        headers: [Host:"${state.tv_ip}:${tv_port}", 'X-Auth-PSK':"${tv_psk}"]
    )
    sendHubCommand(sonycmd)
    log.debug( "hubAction = ${sonycmd}" )
}


def WOLC() {
    log.debug "Executing Wake on Lan"
	def result = new physicalgraph.device.HubAction (
        "wake on lan ${state.tv_mac}",
        physicalgraph.device.Protocol.LAN,
        null,
        [secureCode: "111122223333"]
	)
	return result
}

def getUserTheme(index){
    return getUserTheme(inputTheme ?: state?.theme ?: "default", index)
}

//Themes
def getUserTheme(theme, index){
    switch (theme){
        case "glyphs":
            if (!state?.glyphsTheme){
                return glyphsTheme[index]
            }
            return state.glyphsTheme[index]
            break;
        case "mayssam":
            if (!state?.mayssamTheme){
                return mayssamTheme[index]
            }
            return state.mayssamTheme[index]
            break;
        default:
            if (!state?.defaultTheme){
                return defaultTheme[index]
            }
            return state.defaultTheme[index]
    }
}
