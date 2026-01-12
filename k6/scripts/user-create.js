import http from 'k6/http';
import { check, sleep } from 'k6';
import { randomString, randomIntBetween } from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';

export const options = {
    stages: [
        { duration: '30s', target: 50 },   // 워밍업
        { duration: '1m', target: 200 },   // 부하 증가
        { duration: '1m', target: 200 },   // 유지
        { duration: '30s', target: 0 },    // 종료
    ],
    thresholds: {
        http_req_duration: ['p(95)<500'],  // 95%가 500ms 이하
        http_req_failed: ['rate<0.01'],    // 실패율 1% 이하
    },
};

const BASE_URL = 'http://localhost:8080/api/user';

export default function () {

    const loginId = `user_${randomString(8)}`;

    const payload = JSON.stringify({
        loginId: loginId,
        password: 'password123!',
        name: `user-${randomIntBetween(1, 10000)}`,
        phone: '01012345678',
        email: `${loginId}@test.com`,
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const res = http.post(`${BASE_URL}`, payload, params);

    check(res, {
        'status is 200 or 201': (r) => r.status === 200 || r.status === 201,
    });

    sleep(1);
}
