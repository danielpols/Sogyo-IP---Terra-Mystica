<script setup>

</script>

<template>
    <div class="tileContainer">
        <a class="tile">
            <button type="button" class="tileButton" @click="post">abzbzbz</button>
        </a>
    </div>
</template>

<script>
  export default {
    methods: {
        async post() {
            await fetch('terra/api/log').then(response => response.text())
            .then(data => console.log(data))
            .catch(error => console.log(error));
        }
    }
  }
</script>

<style scoped>
.tileContainer {
    overflow: hidden;
    display: flex;
    justify-content: center;
    flex-direction: column;
    width: 173px;
    height: 200px;
    transform: rotate3d(0, 0, 1, -60deg) skewY(30deg);
}

.tile {
    display: inline-block;
    margin: 0;
    padding: 0;
    width: 173px;
    height: 200px;
    background-color: green;
    transform: skewY(-30deg) rotate3d(0, 0, 1, 60deg);
}

.tileButton {
    width: 100%;
    height: 100%;
}
</style>

