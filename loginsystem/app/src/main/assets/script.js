const canvas = document.querySelector("canvas"),
toolBtns = document.querySelectorAll(".tool"),
sizeSlider = document.querySelector("#size-slider"),

btn = document.querySelector("button"),  // for record screen

colorBtns = document.querySelectorAll(".colors .option"),
colorPicker = document.querySelector("#color-picker"),
ctx = canvas.getContext("2d");

// global variables with default value
let isWriting = false,
selectedTool = "brush",
brushWidth = 5,
selectedColor = "#000";


window.addEventListener("load", ()=> {
    //setting canvas width and height.... offset width/height returns viewable width/height of an element
    canvas.width = canvas.offsetWidth;
    canvas.height = canvas.offsetHeight;
});


const startDraw = () => {
    isWriting = true;
    ctx.beginPath(); // creating new path to draw
    ctx.lineWidth = brushWidth; // passing brush size as line width
    ctx.strokeStyle = selectedColor;
    ctx.fillStyle = selectedColor;
}

const writing = (e) => {
    if(!isWriting) return;  // if isWriting is false return from here

    if(selectedTool === "brush" || selectedTool === "eraser") {
        ctx.strokeStyle = selectedTool === "eraser" ? "#fff" : selectedColor;

        // Use touches[0] to get the first touch point
        const touchX = e.touches[0].pageX - canvas.offsetLeft;
        const touchY = e.touches[0].pageY - canvas.offsetTop;

         //ctx.lineTo(e.offsetX, e.offsetY); // creating line according to the mouse pointer
        ctx.lineTo(touchX, touchY); // creating line according to the touch point
        ctx.stroke(); // drawing/writing/filling line with color
    }
};

toolBtns.forEach(btn => {
    btn.addEventListener("click", () => {
        // adding click event to all tool options
        //removing active class form the previous option and adding on current clicked option
        document.querySelector(".options .active").classList.remove("active");
        btn.classList.add("active");
        selectedTool = btn.id;
        console.log(selectedTool);

    });
});

sizeSlider.addEventListener("change", () => brushWidth = sizeSlider.value); // passing slider value as brush size




colorBtns.forEach(btn => {
    btn.addEventListener("click", () => {
        // adding click event to all color button
        //removing selected class form the previous option and adding on current clicked option
        document.querySelector(".options .selected").classList.remove("selected");
        btn.classList.add("selected");
        // passing selected btn background color as selected color value
        selectedColor = window.getComputedStyle(btn).getPropertyValue("background-color");

    });
});


colorPicker.addEventListener("change", () => {
    // passing picked color value from color picker to last color btn background
    colorPicker.parentElement.style.background = colorPicker.value;
    colorPicker.parentElement.click();
});

canvas.addEventListener("touchstart",startDraw);
canvas.addEventListener("touchmove",writing);
canvas.addEventListener("touchend", () => isWriting = false);



