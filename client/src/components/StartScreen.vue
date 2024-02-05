<script setup>
    import { gameState } from '../global.js';
</script>

<template>
    <div>
        <ul class="playerList">
            <li v-for="(player, i) in playerList" :key="player">
                <input class="playerNameInput" type="text" @input="event => playerList[i].name = event.target.value">
                <select class="playerTerrainInput" @input="event => playerList[i].terrain = event.target.value">
                    <option value="PLAINS">Plains</option>
                    <option value="SWAMP">Swamp</option>
                    <option value="LAKE">Lake</option>
                    <option value="FOREST">Forest</option>
                    <option value="MOUNTAINS">Mountains</option>
                    <option value="WASTELAND">Wasteland</option>
                    <option value="DESERT">Desert</option>
                </select>
                <button v-if="playerList.length > 2" type="button" @click="playerList.splice(i, 1)">Remove</button>
            </li>
            <li v-if="playerList.length < 5"><button type="button" @click="addPlayer">Add another player</button></li>
        </ul>
    </div>
    <button type="button" v-on:click="getGameState" :disabled="disableStart()">Start game!</button>
</template>

<script>
    export default {
        data () {
            return {
                playerList: []
            };
        },
        methods: {
            addPlayer() {
                this.playerList.push({
                    name: "",
                    terrain: "PLAINS"
                });
            },
            removePlayer(index) {
                this.$delete(this.playerList, index);
            },
            async getGameState() {
                await fetch('terra/api/start', {
                method: "POST",
                headers: {
                    Accept: "application/json",
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(this.playerList)
                }).then(response => response.json())
                .then(data => {console.log("Success!"); gameState.state = data})
                .catch(error => console.log(error));
            },
            hasDuplicates(array) {
                return (array.filter((value, index) => array.indexOf(value) != index) != 0);
            },
            getNames() {
                return this.playerList.map(player => player.name);
            },
            getTerrains() {
                return this.playerList.map(player => player.terrain);
            },
            disableStart() {
                if(this.hasDuplicates(this.getNames()) || this.hasDuplicates(this.getTerrains())){
                    return true;
                }
                return false;
            }
        },
        beforeMount() {
            this.addPlayer();
            this.addPlayer();
        }
    }
</script>

<style scoped>
.playerList {
    list-style-type: none;
    padding: 0;
    margin-bottom: 3%;
}
</style>