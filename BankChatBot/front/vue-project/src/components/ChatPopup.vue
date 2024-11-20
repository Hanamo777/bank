<template>
  <div
    v-if="isOpen"
    class="chat-popup"
    ref="chatbox"
    :style="{ left: position.x + 'px', top: position.y + 'px' }"
    @mousedown="startDragging"
    @mousemove="onDragging"
    @mouseup="stopDragging"
    @mouseleave="stopDragging"
  >
    <div class="chat-window">
      <div
        class="chat-header"
        :style="{ cursor: isDragging ? 'grabbing' : 'grab' }"
      >
        <h3>Hana Chat Bot</h3>
        <button @click="closeChat" class="close-btn">×</button>
      </div>
      <div class="chat-messages">
        <div
          v-for="(msg, index) in messages"
          :key="index"
          :class="['message', msg.type]"
        >
          {{ msg.content }}
        </div>
      </div>
      <div class="chat-input">
        <input
          v-model="userInput"
          @keyup.enter="sendMessage"
          placeholder="Enter your message"
        />
        <button @click="sendMessage" class="send-btn">Send</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ChatPopup',
  data() {
    const user = JSON.parse(localStorage.getItem('user'));
    const userId = user ? user.userId : 'guest';
    const storage = user ? localStorage : sessionStorage;
    const chatOpen = storage.getItem(`chatOpen_${userId}`) === 'true';

    return {
      isOpen: chatOpen,
      userInput: '',
      messages: [],
      isDragging: false,
      position: {
        x: 0,
        y: 0,
      },
      dragOffset: {
        x: 0,
        y: 0,
      },
    };
  },
  mounted() {
    // 초기 위치를 화면 중앙으로 설정
    this.centerChat();

    const user = JSON.parse(localStorage.getItem('user'));
    const userId = user ? user.userId : 'guest';
    const storage = user ? localStorage : sessionStorage;
    const savedMessages = storage.getItem(`chatMessages_${userId}`);
    if (savedMessages) {
      this.messages = JSON.parse(savedMessages);
    }
  },
  watch: {
    messages: {
      deep: true,
      handler(newMessages) {
        const user = JSON.parse(localStorage.getItem('user'));
        const userId = user ? user.userId : 'guest';
        const storage = user ? localStorage : sessionStorage;
        storage.setItem(`chatMessages_${userId}`, JSON.stringify(newMessages));
      },
    },
    isOpen(newValue) {
      const user = JSON.parse(localStorage.getItem('user'));
      const userId = user ? user.userId : 'guest';
      const storage = user ? localStorage : sessionStorage;
      storage.setItem(`chatOpen_${userId}`, newValue);
    },
  },
  methods: {
    centerChat() {
      const windowWidth = window.innerWidth;
      const windowHeight = window.innerHeight;
      const chatWidth = 350; // chat-window의 width
      const chatHeight = 500; // chat-window의 height

      this.position.x = (windowWidth - chatWidth) / 2;
      this.position.y = (windowHeight - chatHeight) / 2;
    },
    startDragging(event) {
      if (event.target.closest('.chat-header')) {
        this.isDragging = true;
        this.dragOffset.x = event.clientX - this.position.x;
        this.dragOffset.y = event.clientY - this.position.y;
      }
    },
    onDragging(event) {
      if (this.isDragging) {
        this.position.x = event.clientX - this.dragOffset.x;
        this.position.y = event.clientY - this.dragOffset.y;
      }
    },
    stopDragging() {
      this.isDragging = false;
    },
    async sendMessage() {
      if (!this.userInput.trim()) return;

      try {
        const user = JSON.parse(localStorage.getItem('user'));
        const userId = user ? user.userId : 'guest';

        // 사용자 메시지 추가
        this.messages.push({
          type: 'user',
          content: this.userInput,
        });

        const response = await fetch('http://localhost:8081/api/chat/send', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            message: this.userInput,
            userId: userId,
          }),
        });

        const botResponse = await response.json();

        // 봇 응답 추가
        this.messages.push({
          type: 'bot',
          content: botResponse.message,
        });
      } catch (error) {
        console.error('Error:', error);
      }

      this.userInput = ''; // 입력창 초기화
    },
    clearChat() {
      const user = JSON.parse(localStorage.getItem('user'));
      const userId = user ? user.userId : 'guest';
      const storage = user ? localStorage : sessionStorage;
      this.messages = [];
      storage.removeItem(`chatMessages_${userId}`);
    },
    closeChat() {
      this.isOpen = false;
      const user = JSON.parse(localStorage.getItem('user'));
      const userId = user ? user.userId : 'guest';
      const storage = user ? localStorage : sessionStorage;
      storage.setItem(`chatOpen_${userId}`, false);
    },
  },
};
</script>

<style scoped>
.chat-popup {
  position: fixed;
  z-index: 1000;
}

.chat-window {
  width: 350px;
  height: 500px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  user-select: none; /* 드래그 중 텍스트 선택 방지 */
}

.chat-header {
  padding: 15px;
  background: #00857e;
  color: white;
  border-radius: 10px 10px 0 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  cursor: grab;
}

.chat-header:active {
  cursor: grabbing;
}

.close-btn {
  background: none;
  border: none;
  color: white;
  font-size: 20px;
  cursor: pointer;
}

.chat-messages {
  flex-grow: 1;
  padding: 15px;
  overflow-y: auto;
  user-select: text; /* 메시지 텍스트는 선택 가능하게 */
}

.message {
  margin: 10px 0;
  padding: 10px;
  border-radius: 10px;
  max-width: 80%;
}

.message.user {
  background: #e3f2fd;
  margin-left: auto;
}

.message.bot {
  background: #f5f5f5;
  margin-right: auto;
}

.chat-input {
  padding: 15px;
  border-top: 1px solid #eee;
  display: flex;
  gap: 10px;
}

.chat-input input {
  flex-grow: 1;
  padding: 8px;
  border: 1px solid #f7efef;
  border-radius: 5px;
  outline: none;
}

.send-btn {
  padding: 8px 15px;
  background: #00857e;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.send-btn:hover {
  background: #07d1c7;
}
</style>
