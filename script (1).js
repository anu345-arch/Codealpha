const songs = [
{
    title: "Song 1",
    artist: "Artist 1",
    src: "songs/song1.mp3"
},
{
    title: "Song 2",
    artist: "Artist 2",
    src: "songs/song2.mp3"
},
{
    title: "Song 3",
    artist: "Artist 3",
    src: "songs/song3.mp3"
}
];

const audio = document.getElementById("audio");
const title = document.getElementById("title");
const artist = document.getElementById("artist");
const playBtn = document.getElementById("play");
const nextBtn = document.getElementById("next");
const prevBtn = document.getElementById("prev");
const progress = document.getElementById("progress");
const volume = document.getElementById("volume");
const durationText = document.getElementById("duration");

let currentSong = 0;

loadSong(currentSong);

function loadSong(index){

    audio.src = songs[index].src;
    title.textContent = songs[index].title;
    artist.textContent = songs[index].artist;
}

playBtn.addEventListener("click", () => {

    if(audio.paused){
        audio.play();
        playBtn.textContent = "⏸";
    }
    else{
        audio.pause();
        playBtn.textContent = "▶";
    }
});

nextBtn.addEventListener("click", () => {

    currentSong++;

    if(currentSong >= songs.length){
        currentSong = 0;
    }

    loadSong(currentSong);
    audio.play();
});

prevBtn.addEventListener("click", () => {

    currentSong--;

    if(currentSong < 0){
        currentSong = songs.length - 1;
    }

    loadSong(currentSong);
    audio.play();
});

audio.addEventListener("timeupdate", () => {

    progress.max = audio.duration;

    progress.value = audio.currentTime;

    let current =
    formatTime(audio.currentTime);

    let total =
    formatTime(audio.duration);

    durationText.textContent =
    `${current} / ${total}`;
});

progress.addEventListener("input", () => {
    audio.currentTime = progress.value;
});

volume.addEventListener("input", () => {
    audio.volume = volume.value;
});

function formatTime(time){

    if(isNaN(time)) return "00:00";

    let min = Math.floor(time / 60);
    let sec = Math.floor(time % 60);

    if(sec < 10) sec = "0" + sec;

    return `${min}:${sec}`;
}

/* Autoplay Next Song */

audio.addEventListener("ended", () => {

    currentSong++;

    if(currentSong >= songs.length){
        currentSong = 0;
    }

    loadSong(currentSong);
    audio.play();
});

/* Playlist Click */

document.querySelectorAll("#playlist li")
.forEach(item => {

    item.addEventListener("click", () => {

        currentSong =
        parseInt(item.dataset.index);

        loadSong(currentSong);

        audio.play();

        playBtn.textContent = "⏸";
    });
});