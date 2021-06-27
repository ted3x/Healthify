package ge.c0d3in3.healthify.services

import ge.c0d3in3.components.event_bus.extensions.SPEvent

sealed class StepCountChangedEvent: SPEvent {
    object StepChanged: SPEvent
}