<template>
  <div>
    <h1>한글날 이벤트</h1>
    <div v-if="timeLeft > 0" class="timer">남은 시간: {{ timeLeft }}초</div>
    <div v-else class="timer">게임 종료!</div>

    <table class="game-board">
      <tr v-for="(row, rowIndex) in board" :key="rowIndex">
        <td
          v-for="(cell, colIndex) in row"
          :key="colIndex"
          class="cell"
          :class="{ correct: isCorrect(rowIndex, colIndex) && gameOver }"
          @click="handleClick(rowIndex, colIndex)"
        >
          {{ cell }}
        </td>
      </tr>
    </table>
    <div v-if="gameOver" class="result">
      {{ message }}
    </div>
    <button class="home-button" @click="goHome">홈으로</button>
  </div>
</template>

<script>
import { api } from "@/api";
export default {
  data() {
    return {
      board: [],
      answerPosition: {
        row: 0,
        col: 0,
      },
      timeLeft: 5,
      gameOver: false,
      message: "",
      timer: null,
      result: false,
    };
  },
  created() {
    this.initGame();
    this.startTimer();
  },
  methods: {
    initGame() {
      const rows = 10;
      const cols = 10;
      const defaultChar = "한글";
      const answerChar = "한귤";

      const randomRow = Math.floor(Math.random() * rows);
      const randomCol = Math.floor(Math.random() * cols);

      this.answerPosition = { row: randomRow, col: randomCol };

      this.board = Array.from({ length: rows }, (_, rowIndex) =>
        Array.from({ length: cols }, (_, colIndex) =>
          rowIndex === randomRow && colIndex === randomCol
            ? answerChar
            : defaultChar
        )
      );
    },
    startTimer() {
      this.timer = setInterval(() => {
        if (this.timeLeft > 0) {
          this.timeLeft -= 1;
        } else {
          clearInterval(this.timer);
          this.gameOver = true;
          this.message = "시간 초과!";
        }
      }, 1000);
    },
    handleClick(row, col) {
      if (this.gameOver) return;

      if (row === this.answerPosition.row && col === this.answerPosition.col) {
        this.gameOver = true;
        this.message = "정답입니다!";
        this.result = true;
        clearInterval(this.timer);
      } else {
        this.gameOver = true;
        this.message = "한글이랍니다~";
        this.result = false;
        clearInterval(this.timer);
      }
    },
    isCorrect(row, col) {
      return row === this.answerPosition.row && col === this.answerPosition.col;
    },
    goHome() {
      this.$router.push("/");
    },
  },
};
</script>

<style scoped>
.game-board {
  border-collapse: collapse; /* 셀 간 여백 제거 */
  margin: 0 auto;
  margin-top: 2rem;
}

.cell {
  width: 50px; /* 셀의 너비 */
  height: 50px; /* 셀의 높이 (너비와 동일하게 설정해 정사각형으로) */
  text-align: center;
  vertical-align: middle;
  border: 1px solid #ccc; /* 셀 테두리 */
  background-color: #f5f5f5;
  font-weight: bold;
  color: #00857e;
  cursor: pointer;
  transition: background-color 0.3s;
  padding: 0; /* 내부 여백 제거 */
}

.cell:hover {
  background-color: #e3f2fd;
}

.row {
  display: flex;
}

.timer {
  font-size: 1.5rem;
  font-weight: 500;
  text-align: center;
  color: #777;
  margin-bottom: 1rem;
}

.result {
  font-size: 1.2rem;
  font-weight: bold;
  text-align: center;
  color: #4caf50; /* 성공 메시지 색상 */
  margin-top: 2rem;
}

.cell.correct {
  background-color: #4caf50;
  color: white;
}

.home-button {
  flex: 1;
  padding: 0.75rem;
  border: none;
  border-radius: 0.5rem;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s;
  background-color: #e9ecef;
  color: #495057;
}
</style>
