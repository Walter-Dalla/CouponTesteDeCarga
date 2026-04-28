import uuid
import random
from datetime import datetime, timedelta, timezone
from locust import HttpUser, task, between, events


def future_date():
    dt = datetime.now(timezone.utc) + timedelta(days=random.randint(1, 365))
    return dt.strftime("%Y-%m-%dT%H:%M:%S+00:00")


def coupon_payload():
    return {
        "code": str(uuid.uuid4()),
        "description": f"LOAD-TEST-{random.randint(1000, 9999)}",
        "discountValue": round(random.uniform(0.5, 99.99), 2),
        "expirationDate": future_date(),
        "published": random.choice([True, False]),
    }


class CouponUser(HttpUser):
    wait_time = between(0.5, 2)

    # Tracks codes created during this user's session for GET/DELETE reuse
    created_codes: list[str] = []

    @task(3)
    def create_coupon(self):
        payload = coupon_payload()
        with self.client.post(
            "/coupon",
            json=payload,
            catch_response=True,
        ) as resp:
            if resp.status_code == 201:
                self.created_codes.append(payload["code"])
                resp.success()
            else:
                resp.failure(f"POST /coupon returned {resp.status_code}: {resp.text}")

    @task(5)
    def get_coupon(self):
        if not self.created_codes:
            return
        code = random.choice(self.created_codes)
        with self.client.get(
            f"/coupon/{code}",
            name="/coupon/[code]",
            catch_response=True,
        ) as resp:
            if resp.status_code in (200, 404):
                resp.success()
            else:
                resp.failure(f"GET /coupon/[code] returned {resp.status_code}: {resp.text}")

    @task(1)
    def get_nonexistent_coupon(self):
        """Stress 404 path."""
        fake = str(uuid.uuid4())
        with self.client.get(
            f"/coupon/{fake}",
            name="/coupon/[nonexistent]",
            catch_response=True,
        ) as resp:
            if resp.status_code == 404:
                resp.success()
            else:
                resp.failure(f"Expected 404, got {resp.status_code}: {resp.text}")

    @task(1)
    def delete_coupon(self):
        if not self.created_codes:
            return
        code = self.created_codes.pop(random.randrange(len(self.created_codes)))
        with self.client.delete(
            f"/coupon/{code}",
            name="/coupon/[code] DELETE",
            catch_response=True,
        ) as resp:
            if resp.status_code in (200, 404):
                resp.success()
            else:
                resp.failure(f"DELETE /coupon/[code] returned {resp.status_code}: {resp.text}")
