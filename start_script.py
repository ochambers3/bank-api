import subprocess
import time
import os
import requests

#Start the server using Maven mvn
def start_spring_boot():
    print("Starting Spring Boot application...")
    # Start the Spring Boot application in a separate process
    process = subprocess.Popen(["mvn", "spring-boot:run"], cwd=os.path.dirname(os.path.abspath(__file__)))
    return process

#Method to wait for the server before starting the tests
def wait_for_server(port=8080, timeout=60):
    """ Wait until the server is up and running. """
    start_time = time.time()
    while True:
        try:
            response = requests.get(f"http://localhost:{port}/actuator/health")
            if response.status_code == 200:
                print("Server is up and running.")
                return
        except requests.ConnectionError:
            pass
        if time.time() - start_time > timeout:
            raise TimeoutError("Server did not start in time.")
        time.sleep(5)

#Run the test file BankAPITest.py
def run_tests():
    # Run BankAPITest.py
    print("Running tests...")
    result = subprocess.run(["python", "BankAPITest.py"], cwd=os.path.dirname(os.path.abspath(__file__)), stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    print(result.stdout.decode())
    print(result.stderr.decode())

if __name__ == "__main__":
    spring_boot_process = start_spring_boot()
    try:
        wait_for_server()  # Wait until the server is ready
        run_tests()
    finally:
        # Ensure the Spring Boot process is terminated
        spring_boot_process.terminate()
        spring_boot_process.wait()

