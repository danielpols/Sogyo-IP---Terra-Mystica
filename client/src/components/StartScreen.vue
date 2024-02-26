<script setup>
    import { gameState, hasGameState, getGameState } from '../global.js';
</script>

<template>
    <div>
        <ul class="playerList">
            <li v-for="(player, i) in playerList" :key="player">
                <input class="playerNameInput" type="text" @input="event => playerList[i].name = event.target.value" :placeholder="'Player ' + (i+1)">
                <select class="playerTerrainInput" @input="event => playerList[i].terrain = event.target.value" v-model="player.terrain">
                    <option v-for="terrain in terrainList" :value="terrain">{{ terrain.substring(0, 1) + terrain.substring(1).toLowerCase() }}</option>
                </select>
                <button v-if="playerList.length > 2" type="button" @click="playerList.splice(i, 1)">Remove</button>
            </li>
            <li v-if="playerList.length < 5"><button type="button" @click="addPlayer">Add another player</button></li>
        </ul>
    </div>
    <button type="button" @click="startGame" :disabled="disableStart()">Start game!</button><br/>
    <button v-if="hasExistingGame" type="button" @click="getGameState">Continue game</button>
</template>

<script>
    export default {
        data () {
            return {
                playerList: [],
                terrainList: ["PLAINS", "SWAMP", "LAKE", "FOREST", "MOUNTAINS", "WASTELAND", "DESERT"],
                hasExistingGame : false
            };
        },
        methods: {
            addPlayer() {
                this.playerList.push({
                    name: "",
                    terrain: this.terrainList.filter(terrain => !this.getTerrains().includes(terrain))[0]
                });
            },
            removePlayer(index) {
                this.$delete(this.playerList, index);
            },
            async startGame() {
                await fetch('terra/api/start', {
                method: "POST",
                headers: {
                    Accept: "application/json",
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    players: this.getPlayerList()
                })
                }).then(response => response.json())
                .then(data => gameState.state = data)
                .catch(error => console.log(error));
            },
            hasDuplicates(array) {
                return (array.filter((value, index) => array.indexOf(value) != index) != 0);
            },
            getPlayerList() {
                return this.playerList.map((player, i) => player.name ? player : {name: 'Player ' + (i+1), terrain: player.terrain})
            },
            getNames() {
                return this.getPlayerList().map(player => player.name);
            },
            getTerrains() {
                return this.getPlayerList().map(player => player.terrain);
            },
            disableStart() {
                if(this.getNames().filter(name => (name == '')).length > 0){
                    return true;
                }

                if(this.hasDuplicates(this.getNames()) || this.hasDuplicates(this.getTerrains())){
                    return true;
                }
                return false;
            }
        },
        beforeMount() {
            this.addPlayer();
            this.addPlayer();
            hasGameState().then(data => this.hasExistingGame = data);
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