import { reactive } from "vue";

export const gameState = reactive({
    state: {}
})

export function tileColors(terrain) {
    var colour = "transparent";
    if(terrain == "PLAINS") {
        colour = "saddlebrown";
    } else if(terrain == "SWAMP") {
        colour = "dimgrey";
    } else if(terrain == "LAKE") {
        colour = "royalblue";
    } else if(terrain == "FOREST") {
        colour = "green";
    } else if(terrain == "MOUNTAINS") {
        colour = "silver";
    } else if(terrain == "WASTELAND") {
        colour = "darkred";
    } else if(terrain == "DESERT") {
        colour = "orange";
    } else {
        colour = "navy";
    }
    return colour;
}
