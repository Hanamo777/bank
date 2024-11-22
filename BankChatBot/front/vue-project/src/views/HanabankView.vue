<template>
  <div class="main-container">
    <!-- Header -->
    <Header></Header>
    <!-- Main Content -->
    <main>
      <section class="slider-section">
        <div
          id="carouselExample"
          class="carousel slide"
          data-bs-ride="carousel"
        >
          <div class="carousel-inner">
            <!-- Slide 1 -->
            <div class="carousel-item active">
              <div
                class="d-flex flex-column justify-content-center align-items-start text-white p-5"
                style="min-height: 400px; background-color: #a2e9b4"
              >
                <div
                  class="event-tag bg-light text-success p-2 rounded-pill d-inline-block mb-3"
                >
                  {{ $t('main.carousel.slide1.tag') }}
                </div>
                <h1
                  class="display-4"
                  v-html="
                    $t('main.carousel.slide1.title').replace('\n', '<br />')
                  "
                ></h1>
                <p
                  class="lead"
                  v-html="
                    $t('main.carousel.slide1.desc').replace('\n', '<br />')
                  "
                ></p>
                <button class="btn btn-light mt-4">
                  {{ $t('main.carousel.slide1.button') }}
                </button>
              </div>
            </div>
            <!-- Slide 2 -->
            <div class="carousel-item">
              <div
                class="d-flex flex-column justify-content-center align-items-start text-white p-5"
                style="min-height: 400px; background-color: #ffa07a"
              >
                <div
                  class="event-tag bg-light text-danger p-2 rounded-pill d-inline-block mb-3"
                >
                  {{ $t('main.carousel.slide2.tag') }}
                </div>
                <h1
                  class="display-4"
                  v-html="
                    $t('main.carousel.slide2.title').replace('\n', '<br />')
                  "
                ></h1>
                <p
                  class="lead"
                  v-html="
                    $t('main.carousel.slide2.desc').replace('\n', '<br />')
                  "
                ></p>
                <button class="btn btn-light mt-4">
                  {{ $t('main.carousel.slide2.button') }}
                </button>
              </div>
            </div>
            <!-- Slide 3 -->
            <div class="carousel-item">
              <div
                class="d-flex flex-column justify-content-center align-items-start text-white p-5"
                style="min-height: 400px; background-color: #87ceeb"
              >
                <div
                  class="event-tag bg-light text-primary p-2 rounded-pill d-inline-block mb-3"
                >
                  {{ $t('main.carousel.slide3.tag') }}
                </div>
                <h1
                  class="display-4"
                  v-html="
                    $t('main.carousel.slide3.title').replace('\n', '<br />')
                  "
                ></h1>
                <p
                  class="lead"
                  v-html="
                    $t('main.carousel.slide3.desc').replace('\n', '<br />')
                  "
                ></p>
                <button class="btn btn-light mt-4">
                  {{ $t('main.carousel.slide3.button') }}
                </button>
              </div>
            </div>
          </div>
          <!-- Carousel Controls -->
          <button
            class="carousel-control-prev"
            type="button"
            data-bs-target="#carouselExample"
            data-bs-slide="prev"
          >
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
          </button>
          <button
            class="carousel-control-next"
            type="button"
            data-bs-target="#carouselExample"
            data-bs-slide="next"
          >
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
          </button>
        </div>
      </section>

      <!-- Quick Menu Grid -->
      <section class="quick-menu-grid">
        <div class="menu-card" @click="handleInquiry">
          <HomeIcon class="icon" />
          <span>{{ $t('main.quickMenu.history') }}</span>
        </div>
        <div class="menu-card" @click="handleTransfer">
          <TransferIcon class="icon" />
          <span>{{ $t('main.quickMenu.transfer') }}</span>
        </div>
        <div class="menu-card">
          <BillIcon class="icon" />
          <span>{{ $t('main.quickMenu.bills') }}</span>
        </div>
        <div class="menu-card" @click="exchange">
          <ExchangeIcon class="icon" />
          <span>{{ $t('main.quickMenu.exchange') }}</span>
        </div>
        <div class="menu-card">
          <OpenBankingIcon class="icon" />
          <span>{{ $t('main.quickMenu.openBanking') }}</span>
        </div>
        <div class="menu-card">
          <SecurityIcon class="icon" />
          <span>{{ $t('main.quickMenu.security') }}</span>
        </div>
        <div class="menu-card">
          <QuickViewIcon class="icon" />
          <span>{{ $t('main.quickMenu.quickView') }}</span>
        </div>
        <div class="menu-card">
          <CustomerServiceIcon class="icon" />
          <span>{{ $t('main.quickMenu.customerService') }}</span>
        </div>
      </section>

      <!-- Bottom Menu -->
      <section class="bottom-menu">
        <div class="menu-item">
          <h3>{{ $t('main.bottomMenu.savings.title') }}</h3>
          <p>{{ $t('main.bottomMenu.savings.desc') }}</p>
        </div>
        <div class="menu-item">
          <h3>{{ $t('main.bottomMenu.fund.title') }}</h3>
          <p>{{ $t('main.bottomMenu.fund.desc') }}</p>
        </div>
        <div class="menu-item">
          <h3>{{ $t('main.bottomMenu.forex.title') }}</h3>
          <p>{{ $t('main.bottomMenu.forex.desc') }}</p>
        </div>
        <div class="menu-item">
          <h3>{{ $t('main.bottomMenu.pension.title') }}</h3>
          <p>{{ $t('main.bottomMenu.pension.desc') }}</p>
        </div>
        <div class="menu-item">
          <h3>{{ $t('main.bottomMenu.loan.title') }}</h3>
          <p>{{ $t('main.bottomMenu.loan.desc') }}</p>
        </div>
      </section>

      <!-- News Section -->
      <section class="news-section">
        <div class="news-header">
          <h3>{{ $t('main.news.title') }}</h3>
          <button>{{ $t('main.news.more') }}</button>
        </div>
        <ul class="news-list">
          <li v-for="i in 3" :key="i">
            <span class="news-title">{{
              $t(`main.news.items.${i}.title`)
            }}</span>
            <span class="news-date">{{ $t(`main.news.items.${i}.date`) }}</span>
          </li>
        </ul>
      </section>
    </main>
  </div>
</template>

<script>
import Header from '@/components/Header.vue';
import {
  HomeIcon,
  TransferIcon,
  BillIcon,
  ExchangeIcon,
  OpenBankingIcon,
  SecurityIcon,
  QuickViewIcon,
  CustomerServiceIcon,
} from '@/components/Icons.vue';
import { useI18n } from 'vue-i18n';

export default {
  name: 'HanabankView',
  components: {
    HomeIcon,
    TransferIcon,
    BillIcon,
    ExchangeIcon,
    OpenBankingIcon,
    SecurityIcon,
    QuickViewIcon,
    CustomerServiceIcon,
    Header,
  },
  setup() {
    const { t } = useI18n();
    return { t };
  },
  data() {
    return {
      isLoggedIn: false,
      userName: '',
    };
  },
  created() {
    this.checkLoginStatus();
  },
  methods: {
    checkLoginStatus() {
      const user = JSON.parse(localStorage.getItem('user'));
      if (user) {
        this.isLoggedIn = true;
        this.userName = user.name;
      }
    },
    handleLogout() {
      localStorage.removeItem('user');
      this.isLoggedIn = false;
      this.userName = '';
      this.$router.push('/login');
    },
    handleTransfer() {
      if (!this.isLoggedIn) {
        alert(this.$t('messages.loginRequired'));
        this.$router.push('/login');
        return;
      }
      this.$router.push('/transfer');
    },
    handleInquiry() {
      if (!this.isLoggedIn) {
        alert(this.$t('messages.loginRequired'));
        this.$router.push('/login');
        return;
      }
      this.$router.push('/history');
    },
    exchange() {
      this.$router.push('/exchange');
    },
  },
};
</script>

<style scoped>
/* 기존 스타일 유지 */
.main-container {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto,
    'Helvetica Neue', Arial, sans-serif;
}

.header {
  padding: 0 2rem;
}

.top-menu {
  display: flex;
  justify-content: space-between;
  padding: 0.5rem 0;
  font-size: 0.875rem;
  color: #777;
}

.left-menu span,
.right-menu span {
  margin: 0 1rem;
  cursor: pointer;
}

.main-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 0;
  border-bottom: 1px solid #eee;
}

.nav-items span {
  margin: 0 1.5rem;
  font-weight: 500;
  cursor: pointer;
}

.nav-icons button {
  margin-left: 1rem;
}

.chat-btn {
  background-color: #00857e;
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 1.5rem;
  border: none;
  cursor: pointer;
}

.search-btn,
.menu-btn {
  background: none;
  border: none;
  cursor: pointer;
}

.slider-section {
  position: relative;
  background-size: cover;
  background-position: center;
  color: white;
}

.slider-content h1 {
  font-size: 2.5rem;
  margin: 1rem 0;
  line-height: 1.2;
}

.event-tag {
  display: inline-block;
  padding: 0.25rem 1rem;
  border-radius: 1rem;
}

.cta-button {
  margin-top: 2rem;
  padding: 0.75rem 2rem;
  background-color: white;
  color: #333;
  border: none;
  border-radius: 2rem;
  cursor: pointer;
}

.slider-controls {
  position: absolute;
  bottom: 2rem;
  left: 2rem;
  display: flex;
  align-items: center;
  gap: 1rem;
}

.quick-menu-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 1rem;
  padding: 2rem;
}

.menu-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
  padding: 1rem;
  cursor: pointer;
}

.icon {
  width: 2rem;
  height: 2rem;
  color: #00857e;
}

.bottom-menu {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  background-color: #f5f5f5;
  padding: 2rem;
}

.menu-item {
  text-align: center;
  padding: 1rem;
  cursor: pointer;
}

.menu-item h3 {
  font-weight: 500;
  margin-bottom: 0.5rem;
}

.menu-item p {
  color: #777;
  font-size: 0.875rem;
}

.news-section {
  padding: 2rem;
}

.news-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.news-header button {
  background: none;
  border: none;
  color: #777;
  cursor: pointer;
}

.news-list {
  list-style: none;
  padding: 0;
}

.news-list li {
  display: flex;
  justify-content: space-between;
  padding: 0.75rem 0;
  border-bottom: 1px solid #eee;
  cursor: pointer;
}

.news-date {
  color: #777;
}
</style>
